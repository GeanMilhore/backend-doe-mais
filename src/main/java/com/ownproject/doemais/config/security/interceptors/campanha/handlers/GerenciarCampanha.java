package com.ownproject.doemais.config.security.interceptors.campanha.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.IntermediadorPermissao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.campanha.CampanhaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerenciarCampanha extends IntermediadorPermissao {

    @Autowired
    private CampanhaService campanhaService;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("gerenciar_campanha")){
            if(usuarioLogado.getPerfil().equals("ROLE_ADMIN")) return true;
            Long idCampanha = InterceptorUtils.recuperarIdByUrl(requisicao);
            if (verificarCampanhaPertenceOrganizacaoLogada(idCampanha)) return true;
            throw new IllegalAccessException("Campanha não pertence a organização logada");
        }
        return proximaPermissao(usuarioLogado,requisicao,permissao);
    }

    private boolean verificarCampanhaPertenceOrganizacaoLogada(Long idCampanha) {
        Long idOrganizacaoCampanha = campanhaService.pesquisarCampanha(idCampanha).getOrganizacao().getId();
        Long idOrganizacaoLogada = tokenService.getOrganizacaoLogada().getId();
        return idOrganizacaoLogada == idOrganizacaoCampanha;
    }
}
