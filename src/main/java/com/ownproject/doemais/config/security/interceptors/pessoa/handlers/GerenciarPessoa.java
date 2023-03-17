package com.ownproject.doemais.config.security.interceptors.pessoa.handlers;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import com.ownproject.doemais.config.security.interceptors.IntermediadorPermissao;
import com.ownproject.doemais.domain.pessoa.Pessoa;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.services.pessoa.PessoaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GerenciarPessoa extends IntermediadorPermissao {

    @Autowired
    private PessoaService pessoaService;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("gerenciar_pessoa")){
            verificarPerfilUsuario(usuarioLogado);
            Pessoa pessoaVinculada = pessoaService.recuperarPessoaPorUsuario(usuarioLogado);
            Long idPessoaRequisicao = InterceptorUtils.recuperarIdByUrl(requisicao.getRequestURI());
            if(idPessoaRequisicao == pessoaVinculada.getId()) return true;
            throw new IllegalAccessException("Pessoa logada não é dona da entidade a ser alterada.");
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }

    private static void verificarPerfilUsuario(Usuario usuarioLogado) throws IllegalAccessException  {
        String roleUser = usuarioLogado.getPerfil().getNome();
        if(!roleUser.equals("ROLE_PESSOA") && !roleUser.equals("ROLE_ADMIN"))
            throw new IllegalAccessException("Apenas o dono do registro/entidade é permitido a mudar o estado dela.");
    }
}
