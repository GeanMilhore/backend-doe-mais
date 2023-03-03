package com.ownproject.doemais.controllers.organizacao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrganizacaoEditDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String razaoSocial;
    @NotNull
    private Long idadeInstituicao;
}
