package com.ownproject.doemais.controllers.pessoa.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class PessoaEditDto {
    @NotEmpty
    private String nome;
    @NotNull
    private LocalDateTime dataNascimento;
}
