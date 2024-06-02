package ru.my.spring.boot_security.demo.utils.validaite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String phoneErrors = fieldErrors.stream()
                .filter(fieldError -> fieldError.getField().equals("login"))
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return phoneErrors.isEmpty() ? ResponseEntity.badRequest().body("Ошибка валидации")
                : ResponseEntity.badRequest().body("Ошибка валидации номера телефона: " + phoneErrors);
    }
}
