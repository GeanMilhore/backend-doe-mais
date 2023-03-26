package com.ownproject.doemais.config.security.interceptors.organizacao;

import com.ownproject.doemais.config.security.annotations.HasOrganizacaoPermission;
import com.ownproject.doemais.config.security.interceptors.general.Interceptador;
import com.ownproject.doemais.config.security.interceptors.organizacao.handlers.GerenciarOrganizacao;
import com.ownproject.doemais.config.security.interceptors.organizacao.handlers.VincularOrganizacao;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class OrganizacaoInterceptor extends Interceptador {
    HasOrganizacaoPermission annotation;
    @Override
    public String getRequiredPermission() {
        return annotation.value();
    }
    @Override
    public Boolean methodHasAnnotation(Method method) {
       annotation = method.getAnnotation(HasOrganizacaoPermission.class);
       return (annotation != null);
    }
    OrganizacaoInterceptor(GerenciarOrganizacao gerenciarOrganizacao,
                           VincularOrganizacao vincularOrganizacao){
        super(gerenciarOrganizacao,
                vincularOrganizacao);
    }
}
