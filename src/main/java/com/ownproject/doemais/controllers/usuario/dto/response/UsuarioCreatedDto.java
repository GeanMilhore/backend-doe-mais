package com.ownproject.doemais.controllers.usuario.dto.response;

import com.ownproject.doemais.domain.conta.enums.StatusConta;
import com.ownproject.doemais.domain.perfil.Perfil;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreatedDto {

    private Long id;

    protected StatusConta status;

    private List<Perfil> perfis = new ArrayList<>();

    private String email;

    protected LocalDateTime dataCriacao;

    protected LocalDateTime dataUltimaEdicao;
}
