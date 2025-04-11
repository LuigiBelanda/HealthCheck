package com.healtcheck.labeng.services;

import com.healtcheck.labeng.dtos.AgentLoginDTO;
import com.healtcheck.labeng.dtos.AgentRegisterDTO;
import com.healtcheck.labeng.entities.Agent;

public interface AgentService {
    Agent register(AgentRegisterDTO agentRegisterDTO);
    Agent login(AgentLoginDTO agentLoginDTO);
}
