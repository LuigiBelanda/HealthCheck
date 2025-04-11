package com.healtcheck.labeng.services.impl;

import com.healtcheck.labeng.dtos.AgentLoginDTO;
import com.healtcheck.labeng.dtos.AgentRegisterDTO;
import com.healtcheck.labeng.entities.Agent;
import com.healtcheck.labeng.exceptions.EmailAlreadyRegisteredException;
import com.healtcheck.labeng.exceptions.EmailNotFoundException;
import com.healtcheck.labeng.exceptions.IncorrectPasswordException;
import com.healtcheck.labeng.repositories.AgentRepository;
import com.healtcheck.labeng.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Esta anotação @Service indica que essa classe é um "serviço" dentro do sistema,
// ou seja, ela contém regras e ações que o sistema realiza (como registrar ou logar um usuário).
@Service
public class AgentServiceImpl implements AgentService {

    // Aqui estamos dizendo que o sistema deve automaticamente fornecer (ou injetar)
    // uma instância do AgentRepository, que é a parte do sistema que conversa com o banco de dados.
    @Autowired
    private AgentRepository agentRepository;

    // Este método é chamado quando alguém quer se cadastrar no sistema (fazer o "registro").
    @Override
    public Agent register(AgentRegisterDTO agentRegisterDTO) {
        // Aqui ele verifica se já existe um usuário cadastrado com o e-mail informado.
        // Se sim, ele lança um erro dizendo que o e-mail já está registrado.
        if (agentRepository.findByEmail(agentRegisterDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("E-mail já cadastrado.");
        }

        // Se o e-mail ainda não estiver no sistema, criamos um novo usuário.
        Agent agent = new Agent();
        agent.setName(agentRegisterDTO.getName());         // Definimos o nome
        agent.setEmail(agentRegisterDTO.getEmail());       // Definimos o e-mail
        agent.setPassword(agentRegisterDTO.getPassword()); // Definimos a senha
        agent.setCity(agentRegisterDTO.getCity());         // Definimos a cidade

        // Salvamos esse novo usuário no banco de dados e retornamos ele.
        return agentRepository.save(agent);
    }

    // Este método é chamado quando alguém quer entrar no sistema (fazer "login").
    @Override
    public Agent login(AgentLoginDTO agentLoginDTO) {
        // Primeiro, o sistema tenta encontrar um usuário com o e-mail informado.
        // Se não encontrar, ele mostra um erro dizendo que o e-mail não existe.
        Agent agent = agentRepository.findByEmail(agentLoginDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("E-mail não encontrado."));

        // Se o e-mail existir, agora o sistema verifica se a senha está correta.
        // Se estiver errada, mostra um erro dizendo que a senha está incorreta.
        if (!agent.getPassword().equals(agentLoginDTO.getPassword())) {
            throw new IncorrectPasswordException("Senha incorreta.");
        }

        // Se estiver tudo certo (e-mail e senha corretos), devolvemos o usuário.
        return agent;
    }
}

