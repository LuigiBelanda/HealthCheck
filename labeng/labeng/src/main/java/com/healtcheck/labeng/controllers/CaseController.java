package com.healtcheck.labeng.controllers;

import com.healtcheck.labeng.dtos.CaseRegisterDTO;
import com.healtcheck.labeng.dtos.CaseResponseDTO;
import com.healtcheck.labeng.dtos.CaseSearchRequestDTO;
import com.healtcheck.labeng.dtos.CaseSearchResultDTO;
import com.healtcheck.labeng.exceptions.SearchInvalidException;
import com.healtcheck.labeng.services.CaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
@Tag(name = "Casos", description = "Endpoints para gerenciamento dos casos de doenças")
public class CaseController {

    private final CaseService caseService;
    private final ModelMapper modelMapper;

    @Autowired
    public CaseController(CaseService caseService, ModelMapper modelMapper) {
        this.caseService = caseService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Operation(summary = "Registrar um novo caso", description = "Cadastra um novo caso de doença no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caso registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = CaseResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<CaseResponseDTO> register(@RequestBody @Valid CaseRegisterDTO caseRegisterDTO) {
        CaseResponseDTO response = caseService.register(caseRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar casos de doenças",
            description = "Busca casos de doenças com base nos critérios fornecidos (cidade, bairro, doença)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CaseSearchResultDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida - cidade não informada",
                    content = @Content) // Sem schema específico, pois o corpo da resposta está vazio
    })
    public ResponseEntity<CaseSearchResultDTO> search(@RequestBody CaseSearchRequestDTO searchRequest) {
        if (searchRequest.getCity() == null) {
            throw new SearchInvalidException("Requisição de busca inválida");
        }

        List<CaseResponseDTO> cases = caseService.searchCases(searchRequest);
        CaseSearchResultDTO result = new CaseSearchResultDTO(cases, cases.size());
        return ResponseEntity.ok(result);
    }
}