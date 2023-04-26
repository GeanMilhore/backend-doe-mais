package com.ownproject.doemais.controllers.baseRegistro;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.time.LocalDateTime;


@MappedSuperclass
@Data
public class BaseRegistro {

    protected LocalDateTime dataCriacao;

    protected LocalDateTime dataUltimaEdicao;

    protected LocalDateTime dataExclusao;
}
