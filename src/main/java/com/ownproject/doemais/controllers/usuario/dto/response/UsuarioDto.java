package com.ownproject.doemais.controllers.usuario.dto.response;

import com.ownproject.doemais.models.conta.enums.StatusConta;
import com.ownproject.doemais.models.usuario.enums.TipoUsuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UsuarioDto {

    private Long id;

    private TipoUsuario tipoUsuario;

    private String email;

    private String senha;

    protected StatusConta status;

    protected String nome;

    protected LocalDateTime dataCriacao;

    protected LocalDateTime dataUltimaEdicao;

    protected LocalDateTime dataExclusao;
}
