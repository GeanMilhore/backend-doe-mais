package com.ownproject.doemais.services.password;

import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.repositories.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    public String encriptarSenhaUsuario(Usuario usuario){
        return encriptarString(adicionarSaltSenha(usuario, usuario.getSenha()));
    }

    public String encriptarString(String senha){
        return passwordEncoder.encode(senha);
    }

    public String adicionarSaltSenha(Usuario usuario, String senha){
        String firstSalt = usuario.getDataCriacao().getDayOfWeek().toString();
        String lastSalt = usuario.getEmail().toString();
        return firstSalt + senha + lastSalt;
    }

    public String recuperarUsuarioSenhaSaltPeloEmail(String email, String senha){
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Usúario e/ou Senha Inexistentes."));

        return adicionarSaltSenha(usuarioEncontrado, senha);
    }
}
