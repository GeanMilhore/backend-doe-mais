package com.ownproject.doemais.controllers.doacao.doacaoItemDto.response;

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
}
