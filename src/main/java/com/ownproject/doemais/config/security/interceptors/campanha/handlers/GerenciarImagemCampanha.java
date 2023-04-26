package com.ownproject.doemais.config.security.interceptors.campanha.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.campanha.Campanha;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.campanha.CampanhaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerenciarImagemCampanha extends IntermediadorPermissao {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private CampanhaService campanhaService;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("gerenciar_imagem_campanha")){
            if(usuarioLogado.getPerfil().getNome().equals("ROLE_ADMIN")) return true;
            Long idCampanha = InterceptorUtils.recuperarIdByUrl(requisicao);
            Campanha campanha = campanhaService.pesquisarCampanha(idCampanha);
            if(campanha.getOrganizacao().getId().equals(tokenService.getOrganizacaoLogada().getId())) return true;
            throw new IllegalAccessException("Organização não possui permissão de acesso ou não é dona da campanha.");
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
}
