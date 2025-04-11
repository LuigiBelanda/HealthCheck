package com.healtcheck.labeng.controllers;

import com.healtcheck.labeng.dtos.AgentLoginDTO;
import com.healtcheck.labeng.dtos.AgentRegisterDTO;
import com.healtcheck.labeng.entities.Agent;
import com.healtcheck.labeng.services.AgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AgentController {
    @Autowired
    private AgentService agentService;

    @PostMapping("/register")
    public ResponseEntity<Agent> register(@RequestBody @Valid AgentRegisterDTO agentRegisterDTO) {
        Agent response = agentService.register(agentRegisterDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Agent> login(@RequestBody @Valid AgentLoginDTO agentLoginDTO) {
        Agent response = agentService.login(agentLoginDTO);
        return ResponseEntity.ok(response);
    }
}
