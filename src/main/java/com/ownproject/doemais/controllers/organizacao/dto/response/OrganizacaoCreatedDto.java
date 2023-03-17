package com.ownproject.doemais.controllers.organizacao.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrganizacaoCreatedDto {

    private Long id;
    private String nome;
    private String cnpj;
    private String razaoSocial;
    private Date dataFundacao;

}
