package com.ownproject.doemais.mappers.doacao;

import com.ownproject.doemais.controllers.campanha.dto.response.CampanhaDto;
import com.ownproject.doemais.controllers.doacao.doacaoItemDto.response.DoacaoItemDto;
import com.ownproject.doemais.controllers.doacao.dto.response.DoacaoDto;
import com.ownproject.doemais.domain.campanha.Campanha;
import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoacaoMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public DoacaoMapper(){
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Doacao.class, DoacaoDto.class)
                .addMapping(doacao -> doacao.getPessoa().getId(), DoacaoDto::setIdDoador)
                .addMapping(doacao -> doacao.getPessoa().getNome(), DoacaoDto::setDoador);
    }

    public DoacaoDto doacaoToDoacaoDto(Doacao doacao) {
        List<DoacaoItemDto> itens = doacao.getItens()
                .stream()
                .map(item -> doacaoItemToDoacaoItemDto(item))
                .collect(Collectors.toList());
        DoacaoDto doacaoDto = modelMapper.map(doacao, DoacaoDto.class);
        doacaoDto.setItens(itens);
        return doacaoDto;
    }

    public DoacaoItemDto doacaoItemToDoacaoItemDto(DoacaoItem doacaoItem) {
        return modelMapper.map(doacaoItem, DoacaoItemDto.class);
    }
}
