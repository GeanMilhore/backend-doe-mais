package com.ownproject.doemais.mappers.doacaoValor;

import com.ownproject.doemais.controllers.doacao.doacaoValorDto.response.DoacaoValorDto;
import com.ownproject.doemais.domain.doacao.doacaoValor.DoacaoValor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DoacaoValorMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public DoacaoValorMapper() {
        modelMapper.typeMap(DoacaoValor.class, DoacaoValorDto.class)
                .addMapping(DoacaoValor::getValor, DoacaoValorDto::setValor)
                .addMapping(doacaoValor -> doacaoValor.getPessoa().getId(), DoacaoValorDto::setIdPessoa)
                .addMapping(doacaoValor -> doacaoValor.getPessoa().getNome(), DoacaoValorDto::setPessoa)
                .addMapping(doacaoValor -> doacaoValor.getCampanha().getNome(), DoacaoValorDto::setCampanha)
                .addMapping(doacaoValor -> doacaoValor.getCampanha().getId(), DoacaoValorDto::setIdCampanha);
    }

    public DoacaoValorDto doacaoValorToDoacaoValorDto(DoacaoValor doacaoValor) {
        return modelMapper.map(doacaoValor, DoacaoValorDto.class);
    }
}
