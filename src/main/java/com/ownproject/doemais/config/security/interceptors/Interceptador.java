package com.ownproject.doemais.config.security.interceptors;

import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.usuario.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.stream.Stream;

@Component
public abstract class Interceptador implements HandlerInterceptor {

    @Autowired
    private UsuarioService usuarioService;

    protected Interceptador(IntermediadorPermissao... intermediadores){
        IntermediadorPermissao primeiro = Stream.of(intermediadores).findFirst().get();
        intermediadorPermissao = IntermediadorPermissao.link(
                primeiro,
                intermediadores
        );
    }

    private IntermediadorPermissao intermediadorPermissao;

    public abstract String getRequiredPermission();

    public abstract Boolean methodHasAnnotation(Method metodo);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (methodHasAnnotation(method)) {
                String requiredPermission = getRequiredPermission();
                Usuario logado =  usuarioService.encontrarUsuarioPeloEmail(request.getUserPrincipal().getName());
                InterceptorUtils.verificarPermissaoExecucao(requiredPermission, logado);
                return intermediadorPermissao.verificarPermissao(logado, request, requiredPermission);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {}

}
