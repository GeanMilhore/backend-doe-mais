package com.ownproject.doemais.controllers.usuario.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioPostDto {

    @NotEmpty
    private String tipoUsuario;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String senhaUsuario;
}
