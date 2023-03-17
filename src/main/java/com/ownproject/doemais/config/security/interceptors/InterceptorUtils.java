package com.ownproject.doemais.config.security.interceptors;

import com.ownproject.doemais.domain.autorizacao.Autorizacao;
import com.ownproject.doemais.domain.usuario.Usuario;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterceptorUtils {
    public static void verificarPermissaoExecucao(String requiredPermission, Usuario logado) throws IllegalAccessException {
        boolean possuiPermissao = logado
                .getPerfil()
                .getAutorizacoes()
                .stream()
                .map(Autorizacao::getNome)
                .collect(Collectors.toList())
                .contains(requiredPermission.toUpperCase());

        if(!possuiPermissao) throw new IllegalAccessException("Usuário não possui permissão para executar método");
    }

    public static Long recuperarIdByUrl(String url){
        List<String> splitedUrl = Arrays.asList(url.split("/"));
        return Long.valueOf(splitedUrl.get(splitedUrl.size() - 1));
    }
}
