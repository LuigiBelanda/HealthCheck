package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para autenticação de agentes")
public class AgentLoginDTO {
    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Formato de email inválido")
    @Schema(description = "Email do agente", example = "agente@exemplo.com", required = true)
    private String email;

    @NotBlank(message = "Senha não pode estar vazia")
    @Schema(description = "Senha do agente", example = "senha123", required = true)
    private String password;
}