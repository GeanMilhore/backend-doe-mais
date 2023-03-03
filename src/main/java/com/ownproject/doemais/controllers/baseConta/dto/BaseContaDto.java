package com.ownproject.doemais.controllers.baseConta.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseContaDto {

    protected LocalDateTime dataCriacao;

    protected LocalDateTime dataUltimaEdicao;

    protected LocalDateTime dataExclusao;
}
