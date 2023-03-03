package com.ownproject.doemais.exception.handlers;

public class DomainException extends RuntimeException {

    /*TODO - implementar handler de exceptions no controller para retornar StatusHttp de acordo com a exceção */
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(String message) {
        super(message);
    }
}
