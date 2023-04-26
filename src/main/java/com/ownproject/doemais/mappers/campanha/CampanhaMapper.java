package com.ownproject.doemais.mappers.campanha;

import com.ownproject.doemais.controllers.campanha.dto.request.CampanhaRequestDto;
import com.ownproject.doemais.controllers.campanha.dto.response.CampanhaDto;
import com.ownproject.doemais.domain.campanha.Campanha;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CampanhaMapper {

    private ModelMapper modelMapper;


    public CampanhaMapper() {
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Campanha.class, CampanhaDto.class)
                .addMapping(campanha -> campanha.getOrganizacao().getId(), CampanhaDto::setIdOrganizacao)
                .addMapping(campanha -> campanha.getValores().getValorArrecadado(), CampanhaDto::setValorArrecadado)
                .addMapping(campanha -> campanha.getValores().getQuantidadeDoacoes(), CampanhaDto::setQuantidadeItensDoados)
                .addMapping(campanha -> campanha.getImagem().getUrlImagem(), CampanhaDto::setUrlImagem);
    }

    public CampanhaDto campanhaToCampanhaDto(Campanha campanhaEncontrada) {
        return modelMapper.map(campanhaEncontrada, CampanhaDto.class);
    }

    public Campanha campanhaDtoToCampanha(CampanhaRequestDto campanhaRequestDto) {
        return modelMapper.map(campanhaRequestDto, Campanha.class);
    }
}
