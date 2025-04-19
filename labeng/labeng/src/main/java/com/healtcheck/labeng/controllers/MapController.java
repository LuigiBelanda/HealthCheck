package com.healtcheck.labeng.controllers;


import com.healtcheck.labeng.dtos.CaseMapDTO;
import com.healtcheck.labeng.entities.Case;
import com.healtcheck.labeng.repositories.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/map")
@CrossOrigin(origins = "*")
public class MapController {
    @Autowired
    private CaseRepository caseRepository;

    @GetMapping("/cases")
    public List<CaseMapDTO> getAllCasesForMap() {
        List<Case> cases = caseRepository.findAll();
        return cases.stream()
                .filter(caseItem -> caseItem.getLatitude() != null && caseItem.getLongitude() != null)
                .map(this::convertToMapDTO)
                .collect(Collectors.toList());
    }

    private CaseMapDTO convertToMapDTO(Case caseItem) {
        CaseMapDTO dto = new CaseMapDTO();
        dto.setId(caseItem.getId());
        dto.setDisease(caseItem.getDisease());
        dto.setStreet(caseItem.getStreet());
        dto.setNumber(caseItem.getNumber());
        dto.setNeighborhood(caseItem.getNeighborhood());
        dto.setCity(caseItem.getCity());
        dto.setState(caseItem.getState());
        dto.setLatitude(caseItem.getLatitude());
        dto.setLongitude(caseItem.getLongitude());
        dto.setRegistrationDate(caseItem.getRegistrationDate());
        return dto;
    }
}
