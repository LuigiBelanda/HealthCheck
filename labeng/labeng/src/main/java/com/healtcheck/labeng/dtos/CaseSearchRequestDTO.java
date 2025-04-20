package com.healtcheck.labeng.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseSearchRequestDTO {
    private String city;
    private String neighborhood;
    private String disease;
}