package com.ownproject.doemais.controllers.doacao.doacaoValorDto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDoacaoMonetariaDto {
    private Long idCampanha;

    private BigDecimal valor;
}
