package com.ownproject.doemais.controllers.pessoa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PessoaPostDto {
    @NotBlank(message = "O campo nome é obrigatório")
    protected String nome;
    @NotBlank(message = "O campo cpf é obrigatório")
    private String cpf;
    @NotNull(message = "O campo data de nascimento é obrigatório")
    private Date dataNascimento;

}