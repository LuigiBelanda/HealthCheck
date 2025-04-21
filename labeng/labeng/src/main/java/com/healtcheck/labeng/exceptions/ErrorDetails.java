package com.healtcheck.labeng.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Classe genérica de erros da API")
public class ErrorDetails {
    @Schema(description = "Data e hora em que a exceção foi lançada", example = "2025-04-20T10:42:38.785")
    private LocalDateTime timestamp;

    @Schema(description = "Mensagem de erro da exceção", example = "Email já registrado no sistema")
    private String message;

    @Schema(description = "Detalhes adicionais sobre o erro", example = "uri=/api/agents/register")
    private String details;

    @Schema(description = "Status HTTP da resposta", example = "BAD_REQUEST")
    private HttpStatus status;
}