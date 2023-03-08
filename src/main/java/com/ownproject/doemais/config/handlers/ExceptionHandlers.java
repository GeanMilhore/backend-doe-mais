package com.ownproject.doemais.config.handlers;

import com.ownproject.doemais.config.handlers.exceptions.validationExceptionError.FieldError;
import com.ownproject.doemais.config.handlers.exceptions.validationExceptionError.ValidationExceptionDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity exceptionHandler404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandlerMethodNotValid(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrorList = mapearListaComCamposIncorretos(exception);
        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .fieldErrorList(fieldErrorList)
                        .developerMessage("Faça a correção dos campos e reenvie a requisição.")
                        .details(exception.getBody().getDetail())
                        .title(exception.getBody().getTitle())
                        .timeStamp(LocalDateTime.now())
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
