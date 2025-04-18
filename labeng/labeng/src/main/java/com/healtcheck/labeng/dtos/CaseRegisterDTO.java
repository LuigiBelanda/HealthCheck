package com.healtcheck.labeng.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseRegisterDTO {
    @NotNull(message = "Doença não pode ser nula")
    @NotBlank(message = "Doença não pode estar vazia")
    private String disease;

    // Campos de endereço separados
    @NotNull(message = "Rua não pode ser nula")
    @NotBlank(message = "Rua não pode estar vazia")
    private String street;

    @NotNull(message = "Número não pode ser nulo")
    @NotBlank(message = "Número não pode estar vazio")
    private String number;

    // Complemento é opcional
    private String complement;

    @NotNull(message = "Bairro não pode ser nulo")
    @NotBlank(message = "Bairro não pode estar vazio")
    private String neighborhood;

    @NotNull(message = "Cidade não pode ser nula")
    @NotBlank(message = "Cidade não pode estar vazia")
    private String city;

    @NotNull(message = "Estado não pode ser nulo")
    @NotBlank(message = "Estado não pode estar vazio")
    private String state;

    @NotNull(message = "CEP não pode ser nulo")
    @NotBlank(message = "CEP não pode estar vazio")
    private String zipCode;

    @NotNull(message = "ID do agente não pode ser nulo")
    private Long agentId;
}
