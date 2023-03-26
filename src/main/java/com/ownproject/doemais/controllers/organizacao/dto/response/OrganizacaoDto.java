package com.ownproject.doemais.controllers.organizacao.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

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
