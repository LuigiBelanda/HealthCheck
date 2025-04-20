package com.healtcheck.labeng.controllers;

import com.healtcheck.labeng.dtos.AgentLoginDTO;
import com.healtcheck.labeng.dtos.AgentRegisterDTO;
import com.healtcheck.labeng.dtos.AgentResponseDTO;
import com.healtcheck.labeng.services.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agents")
@Tag(name = "Agentes", description = "Endpoints para gerenciamento de agentes")
public class AgentController {

    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar um novo agente", description = "Cria um novo registro de agente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agente registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já registrado")
    })
    public ResponseEntity<AgentResponseDTO> register(@RequestBody @Valid AgentRegisterDTO agentRegisterDTO) {
        AgentResponseDTO response = agentService.register(agentRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de agente", description = "Autentica um agente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgentResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Email não encontrado"),
            @ApiResponse(responseCode = "401", description = "Senha incorreta")
    })
    public ResponseEntity<AgentResponseDTO> login(@RequestBody @Valid AgentLoginDTO agentLoginDTO) {
        AgentResponseDTO response = agentService.login(agentLoginDTO);
        return ResponseEntity.ok(response);
    }
}