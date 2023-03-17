package com.ownproject.doemais.config.security.interceptors.organizacao.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.IntermediadorPermissao;
import com.ownproject.doemais.domain.organizacao.Organizacao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.organizacao.OrganizacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerenciarOrganizacao extends IntermediadorPermissao {

    @Autowired
    private OrganizacaoService organizacaoService;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("gerenciar_organizacao")){
            verificarPerfilUsuario(usuarioLogado);
            Organizacao organizacaoVinculada = organizacaoService.pesquisarOrganizacaoPorIdUsuario(usuarioLogado);
            Long idOrganizacao = InterceptorUtils.recuperarIdByUrl(requisicao.getRequestURI());
            if(organizacaoVinculada.getId() == idOrganizacao) return true;
            throw new IllegalAccessException("Organização logada não é dona da entidade a ser alterada.");
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }

    private static void verificarPerfilUsuario(Usuario usuarioLogado) throws IllegalAccessException  {
        String roleUser = usuarioLogado.getPerfil().getNome();
        if(!roleUser.equals("ROLE_ORGANIZACAO") && !roleUser.equals("ROLE_ADMIN"))
            throw new IllegalAccessException("Apenas o dono do registro/entidade é permitido a mudar o estado dela.");
    }
}
