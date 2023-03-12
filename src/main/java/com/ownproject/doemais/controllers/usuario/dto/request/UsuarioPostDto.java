package com.ownproject.doemais.controllers.usuario.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioPostDto {

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Long idPerfil;

    @NotEmpty
    private String senhaUsuario;
}
