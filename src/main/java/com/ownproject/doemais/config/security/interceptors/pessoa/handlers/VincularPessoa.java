package com.ownproject.doemais.config.security.interceptors.pessoa.handlers;

import com.ownproject.doemais.config.security.interceptors.general.IntermediadorPermissao;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.repositories.pessoa.PessoaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VincularPessoa extends IntermediadorPermissao {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public boolean verificarPermissao(Usuario usuarioLogado, HttpServletRequest requisicao, String permissao) throws IllegalAccessException {
        if(permissao.equals("vincular_pessoa")){
            if(!usuarioLogado.getPerfil().getNome().equals("ROLE_PESSOA"))
                throw new IllegalAccessException("O usuário deve possuir o perfil de pessoa para continuar a ação.");
            if(pessoaRepository.findPessoaByIdUsuario(usuarioLogado.getId()).isPresent())
                throw new IllegalAccessException("O usuário logado já possui um registro pessoa vínculado.");
            return true;
        }
        return proximaPermissao(usuarioLogado, requisicao, permissao);
    }
}
