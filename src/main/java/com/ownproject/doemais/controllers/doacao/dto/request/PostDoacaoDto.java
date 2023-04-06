package com.ownproject.doemais.controllers.doacao.dto.request;

import com.ownproject.doemais.controllers.doacao.doacaoItemDto.request.PostDoacaoitemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDoacaoDto {
    private Long idCampanha;
    private Long idEnderecoOrigem;
    private Long idEnderecoDestino;
    private List<PostDoacaoitemDto> itens;
}
