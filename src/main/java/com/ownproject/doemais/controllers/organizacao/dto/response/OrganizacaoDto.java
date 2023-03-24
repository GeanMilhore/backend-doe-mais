package com.ownproject.doemais.controllers.organizacao.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ownproject.doemais.controllers.baseConta.BaseContaDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrganizacaoDto {

    private Long id;
    private Long idUsuario;
    private String nome;
    private String cnpj;
    private String razaoSocial;
    private String dataFundacao;
    private String dataCriacao;
    private String dataExclusao;
    private String dataUltimaEdicao;
}
