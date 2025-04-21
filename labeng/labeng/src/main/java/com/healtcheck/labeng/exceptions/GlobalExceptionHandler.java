package com.healtcheck.labeng.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador global de exceções para padronizar as respostas de erro da API
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Trata erros de validação de campos em requisições
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Argumentos inválidos",
                errors.toString(),
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata exceções genéricas não capturadas por outros handlers
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Trata exceção de email já registrado
     */
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyRegisteredException(
            EmailAlreadyRegisteredException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata exceção de senha incorreta
     */
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorDetails> handleIncorrectPasswordException(
            IncorrectPasswordException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.UNAUTHORIZED
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Trata exceção de email não encontrado
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmailNotFoundException(
            EmailNotFoundException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Trata exceção de operação não autorizada
     */
    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ErrorDetails> handleUnauthorizedOperationException(
            UnauthorizedOperationException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.FORBIDDEN
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    /**
     * Trata exceção de recurso não encontrado
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Trata exceção de busca inválida
     */
    @ExceptionHandler(SearchInvalidException.class)
    public ResponseEntity<ErrorDetails> handleSearchInvalidException(
            SearchInvalidException exception,
            WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}