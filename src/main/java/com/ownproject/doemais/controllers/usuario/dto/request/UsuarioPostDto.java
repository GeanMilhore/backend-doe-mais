package com.ownproject.doemais.controllers.usuario.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPostDto {

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Long idPerfil;

    @NotEmpty
    private String senhaUsuario;
}
