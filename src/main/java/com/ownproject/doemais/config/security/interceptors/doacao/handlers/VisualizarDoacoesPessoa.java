package com.ownproject.doemais.config.security.interceptors.doacao.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.authentication.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisualizarDoacoesPessoa extends IntermediadorPermissao {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("visualizar_doacoes_pessoa")){
            if(isUsuarioAdmin(usuarioLogado)) return true;
            verificarDoacoesUsuarioLogado(requisicao);
            return true;
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }

    private void verificarDoacoesUsuarioLogado(HttpServletRequest requisicao) {
        Long idPessoa = Long.valueOf(InterceptorUtils.recuperarPathVariableByUrl(requisicao, "idPessoa"));
        Long idPessoaLogada = tokenService.getPessoaLogada().getId();
        if(idPessoa != idPessoaLogada)
            throw new IllegalArgumentException("Doações não pertencem ao usuário logado");
    }

    private static boolean isUsuarioAdmin(Usuario usuarioLogado) {
        return usuarioLogado.getPerfil().getNome() == "ROLE_ADMIN";
    }
}
