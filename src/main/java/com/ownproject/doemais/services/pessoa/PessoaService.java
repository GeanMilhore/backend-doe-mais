package com.ownproject.doemais.services.pessoa;

import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaEditDto;
import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaPostDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaCreatedDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaDto;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.mappers.pessoa.PessoaMapper;
import com.ownproject.doemais.domain.conta.enums.StatusConta;
import com.ownproject.doemais.domain.pessoa.Pessoa;
import com.ownproject.doemais.repositories.pessoa.PessoaRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PessoaMapper pessoaMapper;

    
    public Pessoa encontrarPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + id));
    }

    public PessoaDto pesquisarPessoa(Long id) {
        return pessoaMapper.toPessoaDto(encontrarPessoa(id));
    }

    public Page<PessoaDto> pesquisarTodasPessoas(Pageable pageable) {
        return pessoaRepository.findAll(pageable).map(pessoa -> pessoaMapper.toPessoaDto(pessoa));
    }


    @Transactional
    public PessoaCreatedDto criarNovaPessoa(PessoaPostDto pessoaDTO) {
        Pessoa pessoa = pessoaMapper.pessoaPostDtoToEntity(pessoaDTO);
        pessoa.setUsuario(tokenService.getUsuarioLogado());
        pessoa.setStatus(StatusConta.ATIVO);
        pessoa.setDataCriacao(DateUtil.dataDeHoje());
        pessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.pessoaToCreatedDto(pessoa);
    }

    @Transactional
    public PessoaDto editarPessoa(Long id, PessoaEditDto pessoaDTO) {
        Pessoa pessoaOriginal = encontrarPessoa(id);
        pessoaMapper.atualizarPessoaPeloDto(pessoaDTO, pessoaOriginal);
        pessoaOriginal.setDataUltimaEdicao(DateUtil.dataDeHoje());
        Pessoa pessoaUpdated = pessoaRepository.save(pessoaOriginal);
        return pessoaMapper.toPessoaDto(pessoaUpdated);
    }


    @Transactional
    public void excluirPessoa(Long id) {
        Pessoa pessoa = encontrarPessoa(id);
        pessoaRepository.delete(pessoa);
    }

    public Pessoa recuperarPessoaPorUsuario(Usuario usuarioLogado) {
        return pessoaRepository
                .findPessoaByIdUsuario(usuarioLogado.getId()).orElseThrow(
                        () -> new EntityNotFoundException("Não existem registro de pessoas vínculadas a esse usuário"));
    }
}

