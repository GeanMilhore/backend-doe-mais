package com.ownproject.doemais.services.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ownproject.doemais.domain.organizacao.Organizacao;
import com.ownproject.doemais.domain.pessoa.Pessoa;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.repositories.organizacao.OrganizacaoRepository;
import com.ownproject.doemais.repositories.pessoa.PessoaRepository;
import com.ownproject.doemais.repositories.usuario.UsuarioRepository;
import com.ownproject.doemais.services.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String gerarToken(Usuario usuario){
        try{
            var algoritmo =  Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("Api Doe+")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token jwt", exception);
        }
    }

    public String getSubject(String tokenJwt){
        try{
            var algoritmo =  Algorithm.HMAC256(secretKey);
            return JWT.require(algoritmo)
                    .withIssuer("Api Doe+")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        } catch (JWTCreationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }

    public DecodedJWT decodeJWT(String jwtToken) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Api Doe+")
                .build();
        return verifier.verify(jwtToken);
    }

    public Usuario getUsuarioLogado(){
        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Erro na recuperação do usuário logado pelo email."));
    }

    public Organizacao getOrganizacaoLogada(){
        Usuario logado = getUsuarioLogado();
        return organizacaoRepository.findOrganizacaoByUsuarioId(logado.getId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("Usuário não possui organização vínculada ou não é do tipo organização"));
    }

    public Pessoa getPessoaLogada(){
        Usuario logado = getUsuarioLogado();
        return pessoaRepository.findPessoaByIdUsuario(logado.getId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("Usuário não possui pessoa vínculada ou não é do tipo pessoa"));
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
