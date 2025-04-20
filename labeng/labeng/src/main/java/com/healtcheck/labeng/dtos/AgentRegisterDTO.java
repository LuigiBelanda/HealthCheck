package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para registro de novos agentes")
public class AgentRegisterDTO {
    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome não pode estar vazio")
    @Schema(description = "Nome completo do agente", example = "João Silva", required = true)
    private String name;

    @NotNull(message = "Email não pode ser nulo")
    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Formato de email inválido")
    @Schema(description = "Email do agente (será usado para login)", example = "joao.silva@exemplo.com", required = true)
    private String email;

    @NotNull(message = "Senha não pode ser nula")
    @NotBlank(message = "Senha não pode estar vazia")
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
    @Schema(description = "Senha do agente (mínimo 8 caracteres)", example = "senha@123", required = true)
    private String password;

    @NotNull(message = "Cidade não pode ser nula")
    @NotBlank(message = "Cidade não pode estar vazia")
    @Schema(description = "Cidade onde o agente atua", example = "São Paulo", required = true)
    private String city;
}