package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resposta com dados do agente")
public class AgentResponseDTO {
    @Schema(description = "ID único do agente", example = "1")
    private Long id;

    @Schema(description = "Nome completo do agente", example = "João Silva")
    private String name;

    @Schema(description = "Email do agente", example = "joao.silva@exemplo.com")
    private String email;

    @Schema(description = "Cidade onde o agente atua", example = "São Paulo")
    private String city;
}