package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resposta para um caso de doença")
public class CaseResponseDTO {
    @Schema(description = "ID único do caso", example = "1")
    private Long id;

    @Schema(description = "Nome da doença", example = "Dengue")
    private String disease;

    @Schema(description = "Nome da rua", example = "Rua das Flores")
    private String street;

    @Schema(description = "Número do endereço", example = "123")
    private String number;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complement;

    @Schema(description = "Nome do bairro", example = "Centro")
    private String neighborhood;

    @Schema(description = "Nome da cidade", example = "São Paulo")
    private String city;

    @Schema(description = "Nome do estado", example = "São Paulo")
    private String state;

    @Schema(description = "CEP do endereço", example = "01234-567")
    private String zipCode;

    @Schema(description = "Data e hora do registro", example = "2023-05-21T14:30:00")
    private LocalDateTime registrationDate;

    @Schema(description = "Latitude da localização", example = "-23.5505")
    private String latitude;

    @Schema(description = "Longitude da localização", example = "-46.6333")
    private String longitude;

    @Schema(description = "Informações básicas do agente responsável")
    private AgentBasicDTO agent;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Informações básicas do agente")
    public static class AgentBasicDTO {
        @Schema(description = "ID do agente", example = "1")
        private Long id;

        @Schema(description = "Nome do agente", example = "João Silva")
        private String name;
    }
}