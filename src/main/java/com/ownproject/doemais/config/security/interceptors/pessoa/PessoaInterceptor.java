package com.ownproject.doemais.config.security.interceptors.pessoa;

import com.ownproject.doemais.config.security.annotations.HasPessoaPermission;
import com.ownproject.doemais.config.security.interceptors.general.Interceptador;
import com.ownproject.doemais.config.security.interceptors.pessoa.handlers.GerenciarPessoa;
import com.ownproject.doemais.config.security.interceptors.pessoa.handlers.VincularPessoa;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PessoaInterceptor extends Interceptador {

    HasPessoaPermission annotation;
    @Override
    public String getRequiredPermission() {
        return annotation.value();
    }

    @Override
    public Boolean methodHasAnnotation(Method method) {
        annotation = method.getAnnotation(HasPessoaPermission.class);
        return (annotation != null);
    }

    PessoaInterceptor(GerenciarPessoa gerenciarPessoa,
                      VincularPessoa vincularPessoa){
        super(gerenciarPessoa, vincularPessoa);
    }
}
