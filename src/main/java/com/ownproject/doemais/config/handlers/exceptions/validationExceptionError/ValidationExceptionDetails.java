package com.ownproject.doemais.config.handlers.exceptions.validationExceptionError;

import com.ownproject.doemais.config.handlers.exceptions.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    List<FieldError> fieldErrorList;
}

