package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resultado da busca de casos")
public class CaseSearchResultDTO {
    @Schema(description = "Lista de casos encontrados")
    private List<CaseResponseDTO> cases;

    @Schema(description = "Total de casos encontrados", example = "5")
    private int totalCases;
}