package com.ownproject.doemais.config.security.interceptors.usuario.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.IntermediadorPermissao;
import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class GerenciarUsuario extends IntermediadorPermissao {
    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("gerenciar_usuario")){
            if(usuarioLogado.getPerfil().getNome().equals("ROLE_ADMIN")) return true;
            Long idUsuario = InterceptorUtils.recuperarIdByUrl(requisicao);
            if(idUsuario == usuarioLogado.getId()) return true;
            throw new IllegalAccessException("Usuário logado não é dono da entidade a ser alterada.");
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
}
