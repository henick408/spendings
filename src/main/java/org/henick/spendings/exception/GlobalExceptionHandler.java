package org.henick.spendings.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchCategoryExistsException.class)
    ResponseEntity<ErrorResponse> handleNoSuchCategoryExists(NoSuchCategoryExistsException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    ResponseEntity<ErrorResponse> handleCategoryAlreadyExists(CategoryAlreadyExistsException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response);
    }

    @ExceptionHandler(NoSuchExpenseExistsException.class)
    ResponseEntity<ErrorResponse> handleNoSuchExpenseExists(NoSuchExpenseExistsException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

}
