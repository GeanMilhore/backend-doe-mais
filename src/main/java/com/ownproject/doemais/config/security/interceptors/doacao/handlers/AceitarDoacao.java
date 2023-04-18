package com.ownproject.doemais.config.security.interceptors.doacao.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.doacao.status.StatusDoacao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.doacao.DoacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AceitarDoacao extends IntermediadorPermissao {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private DoacaoService doacaoService;
    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("aceitar_doacao")){
            Doacao doacaoEncontrada =
                    doacaoService.pesquisarDoacaoPorId(InterceptorUtils.recuperarIdByUrl(requisicao));

            if(doacaoEncontrada.getStatusDoacao().name() != StatusDoacao.PENDENTE.name())
                throw new IllegalArgumentException("A Doação informada não possui o status pendente.");

            if(doacaoPertenceOrganizacaoLogada(doacaoEncontrada))
                throw new IllegalAccessException("Doação não pertence a organização logada");

            return true;
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
    private boolean doacaoPertenceOrganizacaoLogada(Doacao doacaoEncontrada) {
        return tokenService.getOrganizacaoLogada().getId() != doacaoEncontrada.getCampanha().getOrganizacao().getId();
    }
}
