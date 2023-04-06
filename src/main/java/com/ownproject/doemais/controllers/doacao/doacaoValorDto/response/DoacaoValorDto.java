package com.ownproject.doemais.controllers.doacao.doacaoValorDto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoacaoValorDto {
    private Long id;
    private String campanha;
    private Long idCampanha;
    private String pessoa;
    private Long idPessoa;
    private BigDecimal valor;
}
