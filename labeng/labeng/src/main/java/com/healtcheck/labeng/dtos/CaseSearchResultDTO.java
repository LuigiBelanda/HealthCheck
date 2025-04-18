package com.healtcheck.labeng.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseSearchResultDTO {
    private List<CaseSearchRequestDTO> cases;
    private int totalCases;
}
