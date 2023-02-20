package com.ownproject.doemais.controllers.usuario.dto.response;

import com.ownproject.doemais.models.conta.enums.StatusConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreatedDto {

    private Long id;

    protected StatusConta status;

    private String tipoUsuario;

    private String email;

    private String senha;

    protected LocalDateTime dataCriacao;

    protected LocalDateTime dataUltimaEdicao;
}
