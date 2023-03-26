package com.ownproject.doemais.config.security.interceptors.organizacao.handlers;

import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.repositories.organizacao.OrganizacaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VincularOrganizacao extends IntermediadorPermissao {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if (permissao.equals("vincular_organizacao")) {
            verificarPerfilUsuario(usuarioLogado);
            if(organizacaoRepository.findOrganizacaoByUsuarioId(usuarioLogado.getId()).isPresent())
                throw new IllegalAccessException("Usuário já possui uma organização vínculada.");
            return true;
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }

    private static void verificarPerfilUsuario(Usuario usuarioLogado) throws IllegalAccessException {
        if(!usuarioLogado.getPerfil().getNome().equals("ROLE_ORGANIZACAO"))
            throw new IllegalAccessException("O usuário deve possuir perfil de organização");
    }
}
