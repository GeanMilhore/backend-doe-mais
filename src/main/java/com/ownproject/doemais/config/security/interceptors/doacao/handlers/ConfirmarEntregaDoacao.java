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
public class ConfirmarEntregaDoacao extends IntermediadorPermissao {

    @Autowired
    private DoacaoService doacaoService;
    @Autowired
    private TokenService tokenService;
    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("confirmar_entrega_doacao")) {
            Doacao doacao = doacaoService.pesquisarDoacaoPorId(InterceptorUtils.recuperarIdByUrl(requisicao));

            if(!doacaoPertenceOrganizacaoLogada(doacao))
                throw new IllegalAccessException("Doação não pertence a Organização Logada");
            if(doacao.getStatusDoacao().name() != StatusDoacao.ACEITA.name())
                throw new IllegalArgumentException("Doação ainda não foi aceita");

            return true;
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
    private boolean doacaoPertenceOrganizacaoLogada(Doacao doacao) {
        return tokenService.getOrganizacaoLogada().getId() == doacao.getCampanha().getOrganizacao().getId();
    }
}
