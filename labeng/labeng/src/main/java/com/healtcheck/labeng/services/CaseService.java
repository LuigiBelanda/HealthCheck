package com.healtcheck.labeng.services;

import com.healtcheck.labeng.dtos.CaseRegisterDTO;
import com.healtcheck.labeng.dtos.CaseResponseDTO;
import com.healtcheck.labeng.dtos.CaseSearchRequestDTO;

import java.util.List;

public interface CaseService {
    CaseResponseDTO register(CaseRegisterDTO caseRegisterDTO);
    List<CaseResponseDTO> searchCases(CaseSearchRequestDTO searchRequest);
    List<CaseResponseDTO> findByCity(String city);
    List<CaseResponseDTO> findByCityAndNeighborhood(String city, String neighborhood);
    List<CaseResponseDTO> findByCityAndDisease(String city, String disease);
    List<CaseResponseDTO> findByCityAndNeighborhoodAndDisease(String city, String neighborhood, String disease);
}