package com.ownproject.doemais.mappers.organizacao;

import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoEditDto;
import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoPostDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoCreatedDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoDto;
import com.ownproject.doemais.models.organizacao.Organizacao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizacaoMapper {
    
    private ModelMapper modelMapper;

    public OrganizacaoMapper() {
        modelMapper = new ModelMapper();
    }

    public OrganizacaoCreatedDto OrganizacaoToCreatedDto(Organizacao organizacao) {
        return modelMapper.map(organizacao, OrganizacaoCreatedDto.class);
    }

    public Organizacao OrganizacaoPostDtoToEntity(OrganizacaoPostDto organizacaoPostDto) {
        return modelMapper.map(organizacaoPostDto, Organizacao.class);
    }

    public OrganizacaoDto toOrganizacaoDto(Organizacao organizacaoEncontrada) {
        return modelMapper.map(organizacaoEncontrada, OrganizacaoDto.class);
    }

    public List<OrganizacaoDto> allToOrganizacaoDto(List<Organizacao> organizacoes) {
        return organizacoes.stream().map(organizacao -> toOrganizacaoDto(organizacao)).collect(Collectors.toList());
    }

    public void atualizarOrganizacaoPeloDto(OrganizacaoEditDto organizacaoDto, Organizacao Organizacao) {
        Organizacao.setNome(organizacaoDto.getNome());
        Organizacao.setIdadeInstituicao(organizacaoDto.getIdadeInstituicao());
    }
}
