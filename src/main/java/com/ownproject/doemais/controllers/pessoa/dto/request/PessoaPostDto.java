package com.ownproject.doemais.controllers.pessoa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class PessoaPostDto {

    @NotBlank
    protected String nome;
    @NotBlank
    @CPF
    private String cpf;
    @NotNull
    private LocalDateTime dataNascimento;

}