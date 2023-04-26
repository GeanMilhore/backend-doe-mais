package com.ownproject.doemais.controllers.authentication;

import com.ownproject.doemais.controllers.authentication.dto.DadosAutenticacao;
import com.ownproject.doemais.controllers.authentication.dto.DadosNovaSenha;
import com.ownproject.doemais.controllers.authentication.dto.Mensagem;
import com.ownproject.doemais.domain.passwordToken.PasswordToken;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.password.PasswordService;
import com.ownproject.doemais.services.passwordToken.PasswordTokenService;
import com.ownproject.doemais.utils.security.DadosTokenJWT;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthenticationController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private PasswordTokenService passwordTokenService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                dados.login(),
                passwordService.recuperarUsuarioSenhaSaltPeloEmail(dados.login(), dados.senha())
        );
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/solicitar-redefinicao")
    public ResponseEntity<Mensagem> solicitarRedefinicaoSenha(@RequestParam("email") String email){
        passwordTokenService.resetPassword(email);
        return new ResponseEntity<>(new Mensagem("Um email com instruções foi enviado para "+email), HttpStatus.OK);
    }

    @PostMapping("/redefinir")
    public ResponseEntity<Mensagem> redefinirSenha(@RequestBody DadosNovaSenha dados){
        passwordTokenService.redefinirSenha(dados.token(), dados.novaSenha());
        return new ResponseEntity<>(new Mensagem("Sua senha foi redefinida"), HttpStatus.OK);
    }
}
