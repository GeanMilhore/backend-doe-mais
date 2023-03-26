package com.ownproject.doemais.config.security.interceptors.usuario;

import com.ownproject.doemais.config.security.annotations.HasUsuarioPermission;
import com.ownproject.doemais.config.security.interceptors.general.Interceptador;
import com.ownproject.doemais.config.security.interceptors.usuario.handlers.GerenciarUsuario;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class UsuarioInterceptor extends Interceptador {
    private HasUsuarioPermission annotation;
    @Override
    public String getRequiredPermission() {
        return annotation.value();
    }
    @Override
    public Boolean methodHasAnnotation(Method method) {
        annotation = method.getAnnotation(HasUsuarioPermission.class);
        return (annotation != null);
    }
    UsuarioInterceptor(GerenciarUsuario gerenciarUsuario){super(gerenciarUsuario);}
}