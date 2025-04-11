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

// Indica que essa classe é um "Conselheiro Global" de tratamento de exceções.
// Ela intercepta erros lançados por qualquer Controller da aplicação
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // Esse método é chamado automaticamente quando ocorre um erro de validação em argumentos de métodos,
    // geralmente em requisições POST ou PUT com @Valid no DTO.
    // Ex: campos obrigatórios não preenchidos ou formatos inválidos.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Cria um mapa para armazenar os campos e mensagens de erro
        Map<String, String> errors = new HashMap<>();

        // Para cada erro encontrado na validação, pegamos o nome do campo e a mensagem
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        // Cria um objeto de detalhes do erro com data, título, descrição e status HTTP
        // Classe modelo usada para padronizar os detalhes retornados em erros de exceção.
        // Contém informações como data/hora do erro, mensagem, detalhes da requisição e status HTTP.
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Argumentos inválidos", errors.toString(), HttpStatus.BAD_REQUEST);
        // Retorna a resposta com os detalhes e o código HTTP apropriado
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Captura qualquer exceção não tratada especificamente
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

    // Esse método é acionado automaticamente sempre que uma "EmailAlreadyRegisteredException" for lançada
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException exception, WebRequest webRequest) {
        // Cria um objeto de detalhes do erro com data, mensagem personalizada e status 400
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Esse método é acionado quando uma "IncorrectPasswordException" for lançada
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorDetails> handleIncorrectPasswordException(IncorrectPasswordException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.UNAUTHORIZED
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    // Esse método é acionado quando uma "EmailNotFoundException" for lançada
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmailNotFoundException(EmailNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
