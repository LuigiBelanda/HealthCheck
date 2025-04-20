package com.healtcheck.labeng.controllers;

import com.healtcheck.labeng.dtos.CaseRegisterDTO;
import com.healtcheck.labeng.dtos.CaseResponseDTO;
import com.healtcheck.labeng.dtos.CaseSearchRequestDTO;
import com.healtcheck.labeng.dtos.CaseSearchResultDTO;
import com.healtcheck.labeng.services.CaseService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService caseService;
    private final ModelMapper modelMapper;

    @Autowired
    public CaseController(CaseService caseService, ModelMapper modelMapper) {
        this.caseService = caseService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CaseResponseDTO> register(@RequestBody @Valid CaseRegisterDTO caseRegisterDTO) {
        CaseResponseDTO response = caseService.register(caseRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<CaseSearchResultDTO> search(@RequestBody CaseSearchRequestDTO searchRequest) {
        if (searchRequest.getCity() == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CaseResponseDTO> cases = caseService.searchCases(searchRequest);
        CaseSearchResultDTO result = new CaseSearchResultDTO(cases, cases.size());
        return ResponseEntity.ok(result);
    }
}