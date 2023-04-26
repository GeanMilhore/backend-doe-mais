package com.ownproject.doemais.controllers.doacao.doacaoItemDto.response;

import com.ownproject.doemais.controllers.imagem.dto.response.ImagemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoacaoItemDto {

    private Long id;
    private String nome;
    private String descricao;
    private Long idDoacao;
    private String urlImagem;
}
