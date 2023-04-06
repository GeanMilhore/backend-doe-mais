package com.ownproject.doemais.mappers.doacaoItem;

import com.ownproject.doemais.controllers.doacao.doacaoItemDto.request.PostDoacaoitemDto;
import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DoacaoItemMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public DoacaoItem doacaoItemDtoToDoacaoItem(PostDoacaoitemDto doacaoitemDto, Doacao doacao) {
        DoacaoItem novaDoacaoItem = modelMapper.map(doacaoitemDto, DoacaoItem.class);
        novaDoacaoItem.setDoacao(doacao);
        return novaDoacaoItem;
    }
}
