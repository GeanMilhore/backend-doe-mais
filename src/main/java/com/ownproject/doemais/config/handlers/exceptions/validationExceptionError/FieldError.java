package com.ownproject.doemais.config.handlers.exceptions.validationExceptionError;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldError {
    String field;

    String fieldMessage;
}