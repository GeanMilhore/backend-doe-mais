package com.ownproject.doemais.config.security.interceptors.pessoa;

import com.ownproject.doemais.config.security.annotations.HasPessoaPermission;
import com.ownproject.doemais.config.security.annotations.HasUsuarioPermission;
import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.IntermediadorPermissao;
import com.ownproject.doemais.config.security.interceptors.pessoa.handlers.GerenciarPessoa;
import com.ownproject.doemais.config.security.interceptors.pessoa.handlers.VincularPessoa;
import com.ownproject.doemais.config.security.interceptors.usuario.handlers.GerenciarUsuario;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.repositories.pessoa.PessoaRepository;
import com.ownproject.doemais.services.usuario.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

@Component
public class PessoaInterceptor implements HandlerInterceptor {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GerenciarPessoa gerenciarPessoa;
    @Autowired
    private VincularPessoa vincularPessoa;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            HasPessoaPermission annotation = method.getAnnotation(HasPessoaPermission.class);
            if (annotation != null) {
                String requiredPermission = annotation.value();
                Usuario logado =  usuarioService.encontrarUsuarioPeloEmail(request.getUserPrincipal().getName());
                InterceptorUtils.verificarPermissaoExecucao(requiredPermission, logado);

                IntermediadorPermissao permissoes = IntermediadorPermissao.link(
                        vincularPessoa,
                        gerenciarPessoa
                );
                return permissoes.verificarPermissao(logado, request, requiredPermission);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {}
}
