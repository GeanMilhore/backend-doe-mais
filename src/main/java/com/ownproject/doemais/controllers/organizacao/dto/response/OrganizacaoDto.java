package com.ownproject.doemais.controllers.organizacao.dto.response;

import com.ownproject.doemais.controllers.baseConta.BaseContaDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrganizacaoDto extends BaseContaDto {

    private Long id;
    private Long idUsuario;
    private String nome;
    private String cnpj;
    private String razaoSocial;
    private Long idadeInstituicao;

}
