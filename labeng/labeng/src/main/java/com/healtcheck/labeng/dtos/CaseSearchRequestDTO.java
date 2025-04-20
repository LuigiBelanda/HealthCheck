package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para busca de casos de doenças")
public class CaseSearchRequestDTO {
    @Schema(description = "Cidade para filtrar casos", example = "São Paulo", required = true)
    private String city;

    @Schema(description = "Bairro para filtrar casos", example = "Centro")
    private String neighborhood;

    @Schema(description = "Doença para filtrar casos", example = "Dengue")
    private String disease;
}