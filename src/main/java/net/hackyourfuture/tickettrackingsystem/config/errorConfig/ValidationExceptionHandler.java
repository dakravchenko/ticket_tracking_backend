package net.hackyourfuture.tickettrackingsystem.config.errorConfig;

import java.util.Map;
import java.util.stream.Collectors;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnableToExecuteStatementException.class)
    public ResponseEntity<?> handleJdbiConstraintViolation(UnableToExecuteStatementException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("users_email_key")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("email", "A user with this email already exists"));
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Database constraint violation"));
    }
}