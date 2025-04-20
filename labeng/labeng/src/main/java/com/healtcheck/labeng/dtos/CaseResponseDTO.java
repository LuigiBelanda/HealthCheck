package com.healtcheck.labeng.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseResponseDTO {
    private Long id;
    private String disease;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
    private LocalDateTime registrationDate;
    private String latitude;
    private String longitude;
    private AgentBasicDTO agent;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AgentBasicDTO {
        private Long id;
        private String name;
    }
}