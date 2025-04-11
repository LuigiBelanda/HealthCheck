package com.healtcheck.labeng.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentLoginDTO {
    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "Senha não pode estar vazia")
    private String password;
}
