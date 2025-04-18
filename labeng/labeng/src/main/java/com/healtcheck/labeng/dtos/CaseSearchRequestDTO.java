package com.healtcheck.labeng.dtos;

import com.healtcheck.labeng.entities.Case;
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

    public CaseSearchRequestDTO(Case caseEntity) {
        this.disease = caseEntity.getDisease();
        this.neighborhood = caseEntity.getNeighborhood();
        this.city = caseEntity.getCity();
    }
    /*
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
    private String agentName;

    // Construtor que converte de Case para CaseSearchRequestDTO
    public CaseSearchRequestDTO(Case caseEntity) {
        this.id = caseEntity.getId();
        this.disease = caseEntity.getDisease();
        this.street = caseEntity.getStreet();
        this.number = caseEntity.getNumber();
        this.complement = caseEntity.getComplement();
        this.neighborhood = caseEntity.getNeighborhood();
        this.city = caseEntity.getCity();
        this.state = caseEntity.getState();
        this.zipCode = caseEntity.getZipCode();
        this.registrationDate = caseEntity.getRegistrationDate();
        this.agentName = caseEntity.getAgent().getName();
    }
    */
}
