package com.ownproject.doemais.config.security;

import com.ownproject.doemais.config.security.interceptors.organizacao.OrganizacaoInterceptor;
import com.ownproject.doemais.config.security.interceptors.pessoa.PessoaInterceptor;
import com.ownproject.doemais.config.security.interceptors.usuario.UsuarioInterceptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UsuarioInterceptor usuarioInterceptor;
    @Autowired
    private PessoaInterceptor pessoaInterceptor;
    @Autowired
    private OrganizacaoInterceptor organizacaoInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        GerenciadorInterceptadores gerenciador = new GerenciadorInterceptadores(registry);
        gerenciador
                .addInterceptador(usuarioInterceptor)
                .addInterceptador(pessoaInterceptor)
                .addInterceptador(organizacaoInterceptor);
    }
}

@Data
@AllArgsConstructor
class GerenciadorInterceptadores {
    private InterceptorRegistry interceptorRegistry;
    public GerenciadorInterceptadores addInterceptador(HandlerInterceptor handlerInterceptor){
        this.interceptorRegistry.addInterceptor(handlerInterceptor);
        return this;
    }
}
