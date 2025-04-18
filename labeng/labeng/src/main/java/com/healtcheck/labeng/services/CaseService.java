package com.healtcheck.labeng.services;

import com.healtcheck.labeng.dtos.CaseRegisterDTO;
import com.healtcheck.labeng.dtos.CaseSearchRequestDTO;
import com.healtcheck.labeng.entities.Case;

import java.util.List;

public interface CaseService {
    Case register(CaseRegisterDTO caseRegisterDTO);
    List<CaseSearchRequestDTO> findByCity(String city);
    List<CaseSearchRequestDTO> findByCityAndNeighborhood(String city, String neighborhood);
    List<CaseSearchRequestDTO> findByCityAndDisease(String city, String disease);
    List<CaseSearchRequestDTO> findByCityAndNeighborhoodAndDisease(String city, String neighborhood, String disease);
}
