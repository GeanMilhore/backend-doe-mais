package com.ownproject.doemais.domain.passwordToken.status;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum StatusToken {

    ATIVO(1),
    UTILIZADO(2),
    EXPIRADO(3);

    private Integer codigo;

    public Integer getCodigo(){
        return this.codigo;
    }
}
