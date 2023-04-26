package com.ownproject.doemais.controllers.usuario.dto.response;

import com.ownproject.doemais.controllers.imagem.dto.response.ImagemDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDto {
    private Long id;
    private String tipoUsuario;
    private String email;
    private String urlImagem;
}
