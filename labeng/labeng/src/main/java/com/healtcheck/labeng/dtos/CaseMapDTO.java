package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO para representação de caso no mapa")
public class CaseMapDTO {
    @Schema(description = "ID único do caso", example = "1")
    private Long id;

    @Schema(description = "Nome da doença", example = "Dengue")
    private String disease;

    @Schema(description = "Nome da rua", example = "Rua das Flores")
    private String street;

    @Schema(description = "Número do endereço", example = "123")
    private String number;

    @Schema(description = "Nome do bairro", example = "Centro")
    private String neighborhood;

    @Schema(description = "Nome da cidade", example = "Itápolis")
    private String city;

    @Schema(description = "Nome do estado", example = "São Paulo")
    private String state;

    @Schema(description = "Latitude da localização", example = "-23.5505")
    private String latitude;

    @Schema(description = "Longitude da localização", example = "-46.6333")
    private String longitude;

    @Schema(description = "Data e hora do registro", example = "2023-05-21T14:30:00")
    private LocalDateTime registrationDate;
}
