package com.ownproject.doemais.config.security.interceptors.campanha;

import com.ownproject.doemais.config.security.annotations.HasCampanhaPermission;
import com.ownproject.doemais.config.security.interceptors.campanha.handlers.GerenciarImagemCampanha;
import com.ownproject.doemais.config.security.interceptors.general.Interceptador;
import com.ownproject.doemais.config.security.interceptors.campanha.handlers.GerenciarCampanha;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
public class CampanhaInterceptor extends Interceptador {
    private HasCampanhaPermission annotation;
    @Override
    public Boolean methodHasAnnotation(Method method) {
        annotation = method.getAnnotation(HasCampanhaPermission.class);
        return (annotation != null);
    }
    @Override
    public String getRequiredPermission() {
        return annotation.value();
    }

    CampanhaInterceptor(GerenciarCampanha gerenciarCampanha, GerenciarImagemCampanha gerenciarImagemCampanha){
        super(gerenciarCampanha, gerenciarImagemCampanha);
    }
}
