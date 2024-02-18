package dev.tomektomczyk.dataservice;

import dev.tomektomczyk.dataservice.ctrl.dto.InputValidationFailedResponse;
import dev.tomektomczyk.dataservice.ctrl.exception.EmptyTaskListException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(EmptyTaskListException.class)
    public ResponseEntity<InputValidationFailedResponse> handleInputValidationException(EmptyTaskListException e) {
        return ResponseEntity.badRequest().body(
                new InputValidationFailedResponse(e.getMessage(), e.getErrorCode())
        );
    }

}
