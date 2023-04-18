package com.ownproject.doemais.config.handlers;

import com.ownproject.doemais.config.handlers.exceptions.ExceptionDetails;
import com.ownproject.doemais.config.handlers.exceptions.validationExceptionError.FieldError;
import com.ownproject.doemais.config.handlers.exceptions.validationExceptionError.ValidationExceptionDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity exceptionHandler404(EntityNotFoundException exception){
        ExceptionDetails exceptionDetails = criarExceptionDetails(
                exception,
                HttpStatus.NOT_FOUND,
                "Entidade não encontrada.");
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnexpectedTypeException.class})
    public ResponseEntity exceptionHandlerUnexpectedTypeException(Exception exception){
        ExceptionDetails exceptionDetails = criarExceptionDetails(
                exception,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Anotação / Configuração errada para o tipo de campo.");
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity exceptionHandlerIllegalArgumentException(Exception exception){
        ExceptionDetails exceptionDetails = criarExceptionDetails(
                exception,
                HttpStatus.BAD_REQUEST,
                "Argumento incorreto ou ilegal.");
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalAccessException.class})
    public ResponseEntity exceptionHandlerIllegalAccessException(Exception exception){
        ExceptionDetails exceptionDetails = criarExceptionDetails(
                exception,
                HttpStatus.FORBIDDEN,
                "Usuários sem autorização necessária.");
        return new ResponseEntity<>(exceptionDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity exceptionHandlerValorUnicoDuplicado(SQLIntegrityConstraintViolationException exception){
        ExceptionDetails exceptionDetails = criarExceptionDetails(
                exception,
                HttpStatus.BAD_REQUEST,
                "Entidade já registrada no sistema");
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    private static ExceptionDetails criarExceptionDetails(Exception exception, HttpStatus httpStatus, String devMessage) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timeStamp(LocalDateTime.now().toString())
                .title(exception.getMessage())
                .details(exception.toString())
                .status(httpStatus.value())
                .developerMessage(devMessage)
                .build();
        return exceptionDetails;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity exceptionHandlerMethodNotValid(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrorList = mapearListaComCamposIncorretos(exception);
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .fieldErrorList(fieldErrorList)
                        .developerMessage("Faça a correção dos campos e reenvie a requisição.")
                        .details(exception.getBody().getDetail())
                        .title(exception.getBody().getTitle())
                        .timeStamp(LocalDateTime.now().toString())
                        .build()
                , HttpStatus.BAD_REQUEST
        );
    }

    private static List<FieldError> mapearListaComCamposIncorretos(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrorList = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            fieldErrorList.add(
                    FieldError.builder()
                    .field(fieldError.getField())
                    .fieldMessage(fieldError.getDefaultMessage())
                    .build());
        });
        return fieldErrorList;
    }
}
