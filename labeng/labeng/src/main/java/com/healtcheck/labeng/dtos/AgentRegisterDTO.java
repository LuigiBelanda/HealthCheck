package com.healtcheck.labeng.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentRegisterDTO {
    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome não pode estar vazio")
    private String name;

    @NotNull(message = "Email não pode ser nulo")
    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "Senha não pode ser nula")
    @NotBlank(message = "Senha não pode estar vazia")
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
    private String password;

    @NotNull(message = "Cidade não pode ser nula")
    @NotBlank(message = "Cidade não pode estar vazia")
    private String city;
}
