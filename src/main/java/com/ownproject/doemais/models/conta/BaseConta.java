package com.ownproject.doemais.models.conta;

import com.ownproject.doemais.models.conta.enums.StatusConta;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseConta {
    @Enumerated(EnumType.STRING)
    protected StatusConta status;
    protected LocalDateTime dataCriacao;
    protected LocalDateTime dataUltimaEdicao;
    protected LocalDateTime dataExclusao;

}
