package com.healtcheck.labeng.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseMapDTO {
    private Long id;
    private String disease;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
    private LocalDateTime registrationDate;
}
