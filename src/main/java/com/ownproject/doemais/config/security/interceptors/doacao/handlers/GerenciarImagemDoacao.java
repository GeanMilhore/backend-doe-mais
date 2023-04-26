package com.ownproject.doemais.config.security.interceptors.doacao.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import com.ownproject.doemais.domain.doacao.status.StatusDoacao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.doacao.DoacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerenciarImagemDoacao extends IntermediadorPermissao {

    @Autowired
    private DoacaoService doacaoService;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("gerenciar_imagem_doacao")){
            if(usuarioLogado.getPerfil().getNome().equals("ROLE_ADMIN")) return true;
            Long idItem = InterceptorUtils.recuperarIdByUrl(requisicao);
            DoacaoItem doacaoItem = doacaoService.pesquisarItemDoacaoPorId(idItem);
            if(!doacaoItem.getDoacao().getStatusDoacao().equals(StatusDoacao.PENDENTE))
                throw new IllegalArgumentException("Só é possível adicionar uma imagem em uma doação com status pendente.");
            if(doacaoItem.getDoacao().getPessoa().getId().equals(tokenService.getPessoaLogada().getId())) return true;
            throw new IllegalAccessException("Pessoa não possui permissão de acesso ou não é dona da doação.");
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
}
