package com.ownproject.doemais.services.pessoa;

import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaEditDto;
import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaPostDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaCreatedDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaDto;
import com.ownproject.doemais.mappers.pessoa.PessoaMapper;
import com.ownproject.doemais.models.conta.enums.StatusConta;
import com.ownproject.doemais.models.pessoa.Pessoa;
import com.ownproject.doemais.repositories.pessoa.PessoaRepository;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private PessoaMapper pessoaMapper;

    
    public Pessoa encontrarPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada com ID: " + id));
    }

    public PessoaDto detalharPessoa(Long id) {
        return pessoaMapper.toPessoaDto(encontrarPessoa(id));
    }

    public List<PessoaDto> detalharTodasPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoaMapper.allToPessoaDto(pessoas);
    }


    @Transactional
    public PessoaCreatedDto criarNovaPessoa(PessoaPostDto pessoaDTO) {
        Pessoa pessoa = pessoaMapper.pessoaPostDtoToEntity(pessoaDTO);
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
}

