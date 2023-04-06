package com.ownproject.doemais.domain.doacao.status;

public enum StatusDoacao {

    PENDENTE(1l),
    CONFIRMADA(2l),
    ENTREGUE(3l);

    StatusDoacao(Long idCodigo){this.codigo = idCodigo;};

    private Long codigo;

    public Long getCodigo(){
        return this.codigo;
    }
}
