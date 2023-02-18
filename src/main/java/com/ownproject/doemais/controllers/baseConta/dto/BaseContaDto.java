package com.ownproject.doemais.controllers.baseConta.dto;

import com.ownproject.doemais.models.conta.enums.StatusConta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseContaDto {
    protected String nome;

    protected LocalDateTime dataCriacao;

    protected LocalDateTime dataUltimaEdicao;

    protected LocalDateTime dataExclusao;
}
