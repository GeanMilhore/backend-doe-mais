package com.ownproject.doemais.controllers.pessoa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PessoaPostDto {

    @NotNull
    private Long idUsuario;
    @NotBlank
    protected String nome;
    @NotBlank
    private String cpf;
    @NotNull
    private Date dataNascimento;

}