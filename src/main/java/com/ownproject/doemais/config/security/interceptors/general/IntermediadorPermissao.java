package com.ownproject.doemais.config.security.interceptors.general;

import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.servlet.http.HttpServletRequest;

public abstract class IntermediadorPermissao {

    private IntermediadorPermissao proximo;

    public static IntermediadorPermissao link(IntermediadorPermissao primeiro , IntermediadorPermissao... cadeia){
        IntermediadorPermissao topo = primeiro;
        for( IntermediadorPermissao proximoNaCadeia : cadeia){
            if(proximoNaCadeia == primeiro) continue;
            topo.proximo = proximoNaCadeia;
            topo = proximoNaCadeia;
        }
        return primeiro;
    }

    public abstract boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException;

    protected boolean proximaPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(proximo == null){
            return true;
        }
        return proximo.verificarPermissao(usuarioLogado, requisicao, permissao);
    }
}
