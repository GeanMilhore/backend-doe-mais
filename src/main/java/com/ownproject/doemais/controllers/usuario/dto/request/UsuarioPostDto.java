package com.ownproject.doemais.controllers.usuario.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioPostDto {

    @NotEmpty(message = "O tipo do usuário é obrigatório.")
    private String tipoUsuario;

    @NotEmpty(message = "O nome do usuário é obrigatório.")
    private String nomeUsuario;

    @NotEmpty(message = "O email do usuário é obrigatório.")
    @Email
    private String email;

    @NotEmpty(message = "A senha do usuário é obrigatória.")
    private String senhaUsuario;
}
