package com.ownproject.doemais.mappers.doacaoItem;

import com.ownproject.doemais.controllers.doacao.doacaoItemDto.request.PostDoacaoitemDto;
import com.ownproject.doemais.controllers.doacao.doacaoItemDto.response.DoacaoItemDto;
import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DoacaoItemMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public DoacaoItemMapper(){
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(DoacaoItem.class, DoacaoItemDto.class)
                .addMapping(item -> item.getImagem().getUrlImagem(), DoacaoItemDto::setUrlImagem);
    }

    public DoacaoItem doacaoItemDtoToDoacaoItem(PostDoacaoitemDto doacaoitemDto, Doacao doacao) {
        DoacaoItem novaDoacaoItem = modelMapper.map(doacaoitemDto, DoacaoItem.class);
        novaDoacaoItem.setDoacao(doacao);
        return novaDoacaoItem;
    }
}
