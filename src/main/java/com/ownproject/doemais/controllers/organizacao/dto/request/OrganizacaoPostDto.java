package com.ownproject.doemais.controllers.organizacao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrganizacaoPostDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String nomeResponsavel;
    @NotBlank
    @CNPJ
    private String cnpj;
    @NotBlank
    private String razaoSocial;
    @NotNull
    private LocalDateTime dataFundacao;
}
