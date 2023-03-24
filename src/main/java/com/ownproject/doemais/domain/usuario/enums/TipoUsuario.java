package com.ownproject.doemais.domain.usuario.enums;

import com.ownproject.doemais.config.security.interceptors.InterceptorUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum TipoUsuario {
    ADMIN(1),
    PESSOA(2),
    ORGANIZACAO(3);

    private Integer codigoPerfil;

    public Integer getCodigoPerfil(){
        return this.codigoPerfil;
    }

    public static Optional<TipoUsuario> pegarTipoUsuario(String value){
        return Arrays.stream(TipoUsuario.values()).filter(item -> item.name().equals(value)).findFirst();
    }

    public static TipoUsuario pegarTipoUsuario(Integer codigo) {
        return Arrays.stream(TipoUsuario.values()).filter(tipoUsuario -> tipoUsuario.getCodigoPerfil() == codigo)
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Perfil inexistente"));
    }
}
