package com.ownproject.doemais.controllers.usuario.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    private String tipoUsuario;
    private String email;
}
