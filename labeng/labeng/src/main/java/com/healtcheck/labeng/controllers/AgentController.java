package com.healtcheck.labeng.controllers;

import com.healtcheck.labeng.dtos.AgentLoginDTO;
import com.healtcheck.labeng.dtos.AgentRegisterDTO;
import com.healtcheck.labeng.dtos.AgentResponseDTO;
import com.healtcheck.labeng.services.AgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/register")
    public ResponseEntity<AgentResponseDTO> register(@RequestBody @Valid AgentRegisterDTO agentRegisterDTO) {
        AgentResponseDTO response = agentService.register(agentRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AgentResponseDTO> login(@RequestBody @Valid AgentLoginDTO agentLoginDTO) {
        AgentResponseDTO response = agentService.login(agentLoginDTO);
        return ResponseEntity.ok(response);
    }
}