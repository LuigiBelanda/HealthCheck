package com.healtcheck.labeng.services.impl;

import com.healtcheck.labeng.dtos.UserLoginDTO;
import com.healtcheck.labeng.dtos.UserRegisterDTO;
import com.healtcheck.labeng.entities.User;
import com.healtcheck.labeng.exceptions.EmailAlreadyRegisteredException;
import com.healtcheck.labeng.exceptions.EmailNotFoundException;
import com.healtcheck.labeng.exceptions.IncorrectPasswordException;
import com.healtcheck.labeng.repositories.UserRepository;
import com.healtcheck.labeng.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Esta anotação @Service indica que essa classe é um "serviço" dentro do sistema,
// ou seja, ela contém regras e ações que o sistema realiza (como registrar ou logar um usuário).
@Service
public class UserServiceImpl implements UserService {

    // Aqui estamos dizendo que o sistema deve automaticamente fornecer (ou injetar)
    // uma instância do UserRepository, que é a parte do sistema que conversa com o banco de dados.
    @Autowired
    private UserRepository userRepository;

    // Este método é chamado quando alguém quer se cadastrar no sistema (fazer o "registro").
    @Override
    public User register(UserRegisterDTO userRegisterDTO) {
        // Aqui ele verifica se já existe um usuário cadastrado com o e-mail informado.
        // Se sim, ele lança um erro dizendo que o e-mail já está registrado.
        if (userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("E-mail já cadastrado.");
        }

        // Se o e-mail ainda não estiver no sistema, criamos um novo usuário.
        User user = new User();
        user.setName(userRegisterDTO.getName());         // Definimos o nome
        user.setEmail(userRegisterDTO.getEmail());       // Definimos o e-mail
        user.setPassword(userRegisterDTO.getPassword()); // Definimos a senha
        user.setCity(userRegisterDTO.getCity());         // Definimos a cidade

        // Salvamos esse novo usuário no banco de dados e retornamos ele.
        return userRepository.save(user);
    }

    // Este método é chamado quando alguém quer entrar no sistema (fazer "login").
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        // Primeiro, o sistema tenta encontrar um usuário com o e-mail informado.
        // Se não encontrar, ele mostra um erro dizendo que o e-mail não existe.
        User user = userRepository.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("E-mail não encontrado."));

        // Se o e-mail existir, agora o sistema verifica se a senha está correta.
        // Se estiver errada, mostra um erro dizendo que a senha está incorreta.
        if (!user.getPassword().equals(userLoginDTO.getPassword())) {
            throw new IncorrectPasswordException("Senha incorreta.");
        }

        // Se estiver tudo certo (e-mail e senha corretos), devolvemos o usuário.
        return user;
    }
}

