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

// Define que essa classe é um controller REST (responde a requisições HTTP com dados JSON)
@RestController
// Define o prefixo "/map" para todas as rotas deste controller
@RequestMapping("/api/map")
// Permite requisições de qualquer origem (CORS). Ideal para ambiente de desenvolvimento
@CrossOrigin(origins = "*")
public class MapController {

    // Injeta automaticamente o repositório de casos (acesso ao banco de dados)
    @Autowired
    private CaseRepository caseRepository;

    // Rota GET que retorna todos os casos para exibição no mapa
    @GetMapping("/cases")
    public List<CaseMapDTO> getAllCasesForMap() {
        // Busca todos os casos registrados no banco
        List<Case> cases = caseRepository.findAll();

        // Filtra apenas os casos que têm latitude e longitude (necessários para o mapa)
        // Depois, converte cada caso para um DTO específico para o mapa
        return cases.stream()
                .filter(caseItem -> caseItem.getLatitude() != null && caseItem.getLongitude() != null)
                .map(this::convertToMapDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar que converte um objeto "Case" para "CaseMapDTO"
    private CaseMapDTO convertToMapDTO(Case caseItem) {
        CaseMapDTO dto = new CaseMapDTO();

        // Copia os campos do Case para o DTO
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
