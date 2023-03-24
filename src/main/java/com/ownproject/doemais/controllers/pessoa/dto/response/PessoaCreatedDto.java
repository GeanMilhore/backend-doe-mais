package com.ownproject.doemais.controllers.pessoa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ownproject.doemais.controllers.baseConta.BaseContaDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PessoaCreatedDto {
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String dataCriacao;
    private String dataUltimaEdicao;
    private String dataExclusao;
}
