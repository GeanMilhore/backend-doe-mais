package com.ownproject.doemais.mappers.pessoa;

import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaEditDto;
import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaPostDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaCreatedDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaDto;
import com.ownproject.doemais.domain.pessoa.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PessoaMapper {
    
    private ModelMapper modelMapper;

    public PessoaMapper() {
        modelMapper = new ModelMapper();
    }

    public PessoaCreatedDto pessoaToCreatedDto(Pessoa pessoa) {
        return modelMapper.map(pessoa, PessoaCreatedDto.class);
    }

    public Pessoa pessoaPostDtoToEntity(PessoaPostDto pessoaResponseDto) {
        return modelMapper.map(pessoaResponseDto, Pessoa.class);
    }

    public PessoaDto toPessoaDto(Pessoa pessoaEncontrado) {
        return modelMapper.map(pessoaEncontrado, PessoaDto.class);
    }

    public List<PessoaDto> allToPessoaDto(List<Pessoa> pessoas) {
        return pessoas.stream().map(pessoa -> toPessoaDto(pessoa)).collect(Collectors.toList());
    }

    public void atualizarPessoaPeloDto(PessoaEditDto pessoaDTO, Pessoa pessoa) {
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
    }
}
