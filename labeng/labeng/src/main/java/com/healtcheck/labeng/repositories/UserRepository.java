/*
Este arquivo é responsável por fazer a "ponte" entre o sistema e o banco de dados, mais especificamente
com a tabela de usuários. Ele permite que o sistema consiga buscar usuários pelo e-mail,
cadastrar novos, editar ou remover usuários de forma prática e organizada, sem precisar escrever
comandos complicados de banco de dados.

O que é um Repository?
É uma interface que define métodos de acesso a dados. Ela fica responsável por interagir com a
base de dados, e quando você estende uma interface como JpaRepository, o Spring
automaticamente implementa os métodos básicos pra você. Ou seja:

Você escreve a interface, o Spring escreve a implementação mágica por trás.
*/

package com.healtcheck.labeng.repositories;

import com.healtcheck.labeng.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Aqui estamos dizendo que a interface UserRepository vai herdar (ou estender)
// as funcionalidades do JpaRepository. Isso significa que já teremos vários
// métodos prontos para salvar, buscar, deletar e atualizar usuários no banco de dados.
public interface UserRepository extends JpaRepository<User, Long> {
    // Este método é usado para buscar um usuário no banco de dados pelo e-mail.
    // Ele retorna um Optional<User>, o que quer dizer que o resultado pode ser:
    // - Um usuário encontrado com aquele e-mail, ou
    // - Nada (caso não exista nenhum usuário com aquele e-mail).
    // findByEmail(String email) → O Spring vê esse nome e
    // entende que você quer buscar um usuário pelo campo email. Ele cria o SQL internamente!
    Optional<User> findByEmail(String email);
}
