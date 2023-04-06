package com.ownproject.doemais.config.security.interceptors.doacao;

import com.ownproject.doemais.config.security.annotations.HasDoacaoPermission;
import com.ownproject.doemais.config.security.interceptors.general.Interceptador;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class DoacaoInterceptor extends Interceptador {

    private HasDoacaoPermission annotation;

    @Override
    public String getRequiredPermission() {
        return annotation.value();
    }

    @Override
    public Boolean methodHasAnnotation(Method method) {
        annotation = method.getAnnotation(HasDoacaoPermission.class);
        return (annotation != null);
    }

    DoacaoInterceptor(){
        super();
    }
}
