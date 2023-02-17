package com.ownproject.doemais.models.usuario.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum TipoUsuario {
    ORGANIZACAO,
    PESSOA;

    public static Optional<TipoUsuario> pegarTipoUsuario(String value){
        return Arrays.stream(TipoUsuario.values()).filter(item -> item.name().equals(value)).findFirst();
    }
}
