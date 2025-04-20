package com.healtcheck.labeng.services;

import com.healtcheck.labeng.dtos.AgentLoginDTO;
import com.healtcheck.labeng.dtos.AgentRegisterDTO;
import com.healtcheck.labeng.dtos.AgentResponseDTO;

public interface AgentService {
    AgentResponseDTO register(AgentRegisterDTO agentRegisterDTO);
    AgentResponseDTO login(AgentLoginDTO agentLoginDTO);
}
