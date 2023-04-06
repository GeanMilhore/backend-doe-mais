package com.ownproject.doemais.controllers.endereco.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoPostDto {
    @Pattern(regexp = "[0-9]{5}-[\\d]{3}")
    private String cep;
    @NotEmpty
    private String logradouro;
    private String complemento;
    @NotEmpty
    private String bairro;
    @NotEmpty
    private String localidade;
    @NotEmpty
    private String uf;
    @NotNull
    private Long numero;
}