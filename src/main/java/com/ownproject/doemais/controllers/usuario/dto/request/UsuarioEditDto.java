package com.ownproject.doemais.controllers.usuario.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioEditDto {

    @NotEmpty
    protected String nome;
}
