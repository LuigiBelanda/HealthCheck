package com.healtcheck.labeng.controllers;

import com.healtcheck.labeng.dtos.CaseRegisterDTO;
import com.healtcheck.labeng.dtos.CaseSearchRequestDTO;
import com.healtcheck.labeng.dtos.CaseSearchResultDTO;
import com.healtcheck.labeng.entities.Case;
import com.healtcheck.labeng.services.CaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cases")
public class CaseController {
    @Autowired
    private CaseService caseService;

    @PostMapping("/register")
    public ResponseEntity<Case> register(@RequestBody @Valid CaseRegisterDTO caseRegisterDTO) {
        Case response = caseService.register(caseRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Nova rota única para buscar por cidade, bairro e/ou doença via JSON no corpo da requisição
    @GetMapping("/search")
    public ResponseEntity<CaseSearchResultDTO> search(@RequestBody CaseSearchRequestDTO searchRequest) {
        List<CaseSearchRequestDTO> cases;

        String city = searchRequest.getCity();
        String neighborhood = searchRequest.getNeighborhood();
        String disease = searchRequest.getDisease();

        if (city != null && neighborhood != null && disease != null) {
            cases = caseService.findByCityAndNeighborhoodAndDisease(city, neighborhood, disease);
        } else if (city != null && neighborhood != null) {
            cases = caseService.findByCityAndNeighborhood(city, neighborhood);
        } else if (city != null && disease != null) {
            cases = caseService.findByCityAndDisease(city, disease);
        } else if (city != null) {
            cases = caseService.findByCity(city);
        } else {
            return ResponseEntity.badRequest().build();
        }

        CaseSearchResultDTO result = new CaseSearchResultDTO(cases, cases.size());
        return ResponseEntity.ok(result);
    }

    /*
    // Pesquisar casos por cidade
    @GetMapping("/search/city/{city}")
    public ResponseEntity<List<CaseSearchRequestDTO>> findByCity(@PathVariable String city) {
        List<CaseSearchRequestDTO> cases = caseService.findByCity(city);
        return ResponseEntity.ok(cases);
    }

    // Pesquisar casos por cidade e bairro
    @GetMapping("/search/city/{city}/neighborhood/{neighborhood}")
    public ResponseEntity<List<CaseSearchRequestDTO>> findByCityAndNeighborhood(
            @PathVariable String city,
            @PathVariable String neighborhood) {
        List<CaseSearchRequestDTO> cases = caseService.findByCityAndNeighborhood(city, neighborhood);
        return ResponseEntity.ok(cases);
    }

    // Pesquisar casos por cidade e doença
    @GetMapping("/search/city/{city}/disease/{disease}")
    public ResponseEntity<List<CaseSearchRequestDTO>> findByCityAndDisease(
            @PathVariable String city,
            @PathVariable String disease) {
        List<CaseSearchRequestDTO> cases = caseService.findByCityAndDisease(city, disease);
        return ResponseEntity.ok(cases);
    }

    // Pesquisar casos por cidade, bairro e doença
    @GetMapping("/search/city/{city}/neighborhood/{neighborhood}/disease/{disease}")
    public ResponseEntity<List<CaseSearchRequestDTO>> findByCityAndNeighborhoodAndDisease(
            @PathVariable String city,
            @PathVariable String neighborhood,
            @PathVariable String disease) {
        List<CaseSearchRequestDTO> cases = caseService.findByCityAndNeighborhoodAndDisease(city, neighborhood, disease);
        return ResponseEntity.ok(cases);
    }
    */
}
