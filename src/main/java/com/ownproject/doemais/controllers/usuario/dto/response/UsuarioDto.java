package com.ownproject.doemais.controllers.usuario.dto.response;

import com.ownproject.doemais.controllers.baseConta.dto.BaseContaDto;
import com.ownproject.doemais.models.usuario.enums.TipoUsuario;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDto extends BaseContaDto {

    private Long id;

    private TipoUsuario tipoUsuario;

    private String email;

    private String senha;

}
