package com.ownproject.doemais.mappers.organizacao;

import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoEditDto;
import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoPostDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoCreatedDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioCreatedDto;
import com.ownproject.doemais.domain.organizacao.Organizacao;
import com.ownproject.doemais.domain.usuario.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizacaoMapper {
    
    private ModelMapper modelMapper;

    public OrganizacaoMapper() {
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Organizacao.class, OrganizacaoCreatedDto.class)
                .addMapping(Organizacao::getDataFundacao, OrganizacaoCreatedDto::setDataFundacao);

        modelMapper.createTypeMap(Organizacao.class, OrganizacaoDto.class)
                .addMapping(Organizacao::getDataFundacao, OrganizacaoDto::setDataFundacao)
                .addMapping(Organizacao::getDataCriacao, OrganizacaoDto::setDataCriacao)
                .addMapping(Organizacao::getDataExclusao, OrganizacaoDto::setDataExclusao)
                .addMapping(Organizacao::getDataUltimaEdicao, OrganizacaoDto::setDataUltimaEdicao);
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
        Organizacao.setDataFundacao(organizacaoDto.getDataFundacao());
    }
}
