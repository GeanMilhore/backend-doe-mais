package com.ownproject.doemais.config.security.interceptors.endereco.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class GerenciarEndereco extends IntermediadorPermissao {
    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("gerenciar_endereco")){
            Long idEndereco = InterceptorUtils.recuperarIdByUrl(requisicao);
            usuarioLogado
                    .getEnderecos()
                    .stream()
                    .map(endereco -> endereco.getId())
                    .filter(id -> id == idEndereco)
                    .findAny()
                    .orElseThrow(() -> new IllegalAccessException("Endereço inexistente ou não pertencente ao usuário"));
            return true;
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
}
