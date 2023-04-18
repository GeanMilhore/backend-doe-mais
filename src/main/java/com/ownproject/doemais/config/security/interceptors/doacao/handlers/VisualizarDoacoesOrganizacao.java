package com.ownproject.doemais.config.security.interceptors.doacao.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.authentication.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisualizarDoacoesOrganizacao extends IntermediadorPermissao {
    @Autowired
    private TokenService tokenService;
    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("visualizar_doacoes_organizacao")){
            if(isUsuarioAdmin(usuarioLogado)) return true;
            verificarOrganizacaoDonaDoacoes(requisicao);
            return true;
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
    private void verificarOrganizacaoDonaDoacoes(HttpServletRequest requisicao) {
        Long idOng = Long.valueOf(InterceptorUtils.recuperarPathVariableByUrl(requisicao, "idOng"));
        Long idOrganizacaoLogada = tokenService.getOrganizacaoLogada().getId();
        if(idOng != idOrganizacaoLogada)
            throw new IllegalArgumentException("Doação não pertence a organização logada");
    }
    private boolean isUsuarioAdmin(Usuario logado) {
        return logado.getPerfil().getNome() == "ROLE_ADMIN";
    }
}
