package com.ownproject.doemais.services.passwordToken;

import com.ownproject.doemais.domain.email.EmailModel;
import com.ownproject.doemais.domain.passwordToken.PasswordToken;
import com.ownproject.doemais.domain.passwordToken.status.StatusToken;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.repositories.passwordToken.PasswordTokenRepository;
import com.ownproject.doemais.repositories.usuario.UsuarioRepository;
import com.ownproject.doemais.services.email.EmailService;
import com.ownproject.doemais.services.password.PasswordService;
import com.ownproject.doemais.services.usuario.UsuarioService;
import com.ownproject.doemais.utils.data.DateUtil;
import com.ownproject.doemais.utils.security.TokenGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordTokenService {

    @Autowired
    private PasswordTokenRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordService passwordService;

    @Value("${projeto.notificacoes.email}")
    private String emailProjeto;

    @Transactional
    public void resetPassword(String userEmail) {
        usuarioService.encontrarUsuarioPeloEmail(userEmail);
        String token = TokenGenerator.generateToken();
        PasswordToken passwordToken = PasswordToken
                .builder()
                .tokenPassword(token)
                .emailUsuario(userEmail)
                .status(StatusToken.ATIVO)
                .tempoExpiracao(getTimeMillisMaisMeiaHora())
                .dataCriacao(DateUtil.dataDeHoje())
                .build();

        enviarEmailNovaSenha(passwordToken);
        repository.save(passwordToken);
    }

    public void enviarEmailNovaSenha(PasswordToken token){
        EmailModel email = new EmailModel();
        email.setEmailFrom(emailProjeto);
        email.setEmailTo(token.getEmailUsuario());
        email.setText("Utilize o Token a seguir para realizar a troca de senha dentro do sistema. \n" +
                        "token: " + token.getTokenPassword());
        email.setSubject("Redefina sua Senha - Token para redifinição de senha.");
        email.setOwnerRef("Doe+");
        emailService.sendEmail(email);
    }

    @Transactional
    public void redefinirSenha(String tokenRedefinicao, String novaSenha){
        PasswordToken token = pesquisarTokenPasswordByToken(tokenRedefinicao);
        verificarTokenValido(token);
        Usuario usuario = usuarioService.encontrarUsuarioPeloEmail(token.getEmailUsuario());
        usuario.setSenha(novaSenha);
        usuario.setSenha(passwordService.encriptarSenhaUsuario(usuario));
        token.setStatus(StatusToken.UTILIZADO);
        repository.save(token);
        usuarioRepository.save(usuario);
    }

    private PasswordToken pesquisarTokenPasswordByToken(String tokenRedefinicao) {
        return repository.pesquisarPasswordTokenPorToken(tokenRedefinicao)
                .orElseThrow(() -> new RuntimeException(new IllegalAccessException("Token Ínvalido")));
    }

    private void verificarTokenValido(PasswordToken token) {
        if(token.estaEspirado()) {
            repository.save(token);
            throw new IllegalArgumentException("O token informado está expirado");
        }
        if(!token.estaAtivo())
            throw new IllegalArgumentException("O token informado já foi utilizado");
    }

    public Long getTimeMillisMaisMeiaHora(){
        long currentTimeMillis = System.currentTimeMillis();
        long halfHourInMillis = 30 * 60 * 1000;
        return currentTimeMillis + halfHourInMillis;
    }
}
