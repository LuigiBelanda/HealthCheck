package com.healtcheck.labeng.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String city;
}