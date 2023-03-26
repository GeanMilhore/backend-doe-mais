package com.ownproject.doemais.controllers.baseRegistro;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseRegistro {

    protected LocalDateTime dataCriacao;

    protected LocalDateTime dataUltimaEdicao;

    protected LocalDateTime dataExclusao;
}
