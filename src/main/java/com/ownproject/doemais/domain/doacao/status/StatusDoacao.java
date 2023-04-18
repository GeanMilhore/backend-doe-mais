package com.ownproject.doemais.domain.doacao.status;

public enum StatusDoacao {

    PENDENTE(1l),
    ACEITA(2l),
    ENTREGUE(3l),
    RECUSADA(4L);

    StatusDoacao(Long idCodigo){this.codigo = idCodigo;};

    private Long codigo;

    public Long getCodigo(){
        return this.codigo;
    }
}
