package com.healtcheck.labeng.services.impl;

import com.healtcheck.labeng.dtos.AgentLoginDTO;
import com.healtcheck.labeng.dtos.AgentRegisterDTO;
import com.healtcheck.labeng.dtos.AgentResponseDTO;
import com.healtcheck.labeng.entities.Agent;
import com.healtcheck.labeng.exceptions.EmailAlreadyRegisteredException;
import com.healtcheck.labeng.exceptions.EmailNotFoundException;
import com.healtcheck.labeng.exceptions.IncorrectPasswordException;
import com.healtcheck.labeng.repositories.AgentRepository;
import com.healtcheck.labeng.services.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, ModelMapper modelMapper) {
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /*
    O @Transactional do Spring faz com que o método seja executado dentro de uma
    transação de banco de dados. Isso significa que todas as operações dentro
    do método são tratadas como uma única transação: ou tudo dá certo e
    é confirmado (commit), ou se der algum erro, tudo é desfeito automaticamente (rollback).
    */
    @Override
    @Transactional
    public AgentResponseDTO register(AgentRegisterDTO agentRegisterDTO) {
        // Verificar se o email já existe
        agentRepository.findByEmail(agentRegisterDTO.getEmail())
                .ifPresent(agent -> {
                    throw new EmailAlreadyRegisteredException("E-mail já cadastrado.");
                });

        // Mapear o DTO para a entidade
        Agent agent = modelMapper.map(agentRegisterDTO, Agent.class);

        // Criptografar senha
        agent.setPassword(passwordEncoder.encode(agentRegisterDTO.getPassword()));

        // Salvar e mapear para DTO de resposta
        Agent savedAgent = agentRepository.save(agent);
        return modelMapper.map(savedAgent, AgentResponseDTO.class);
    }

    @Override
    public AgentResponseDTO login(AgentLoginDTO agentLoginDTO) {
        // Buscar agente pelo email
        Agent agent = agentRepository.findByEmail(agentLoginDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("E-mail não encontrado."));

        // Verificar senha
        if (!passwordEncoder.matches(agentLoginDTO.getPassword(), agent.getPassword())) {
            throw new IncorrectPasswordException("Senha incorreta.");
        }

        // Mapear para DTO de resposta
        return modelMapper.map(agent, AgentResponseDTO.class);
    }
}