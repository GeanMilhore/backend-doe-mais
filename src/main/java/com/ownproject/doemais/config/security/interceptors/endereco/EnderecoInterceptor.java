package com.ownproject.doemais.config.security.interceptors.endereco;

import com.ownproject.doemais.config.security.annotations.HasEnderecoPermission;
import com.ownproject.doemais.config.security.interceptors.endereco.handlers.GerenciarEndereco;
import com.ownproject.doemais.config.security.interceptors.general.Interceptador;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class EnderecoInterceptor extends Interceptador {

    private HasEnderecoPermission annotation;

    @Override
    public String getRequiredPermission() {
        return annotation.value();
    }

    @Override
    public Boolean methodHasAnnotation(Method method) {
        annotation = method.getAnnotation(HasEnderecoPermission.class);
        return (annotation != null);
    }

    EnderecoInterceptor(GerenciarEndereco gerenciarEndereco){
        super(gerenciarEndereco);
    }
}
