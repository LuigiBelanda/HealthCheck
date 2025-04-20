package com.healtcheck.labeng.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para registro de um novo caso de doença")
public class CaseRegisterDTO {
    @NotNull(message = "Doença não pode ser nula")
    @NotBlank(message = "Doença não pode estar vazia")
    @Schema(description = "Nome da doença", example = "Dengue", required = true)
    private String disease;

    @NotNull(message = "Rua não pode ser nula")
    @NotBlank(message = "Rua não pode estar vazia")
    @Schema(description = "Nome da rua", example = "Rua das Flores", required = true)
    private String street;

    @NotNull(message = "Número não pode ser nulo")
    @NotBlank(message = "Número não pode estar vazio")
    @Schema(description = "Número do endereço", example = "123", required = true)
    private String number;

    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complement;

    @NotNull(message = "Bairro não pode ser nulo")
    @NotBlank(message = "Bairro não pode estar vazio")
    @Schema(description = "Nome do bairro", example = "Centro", required = true)
    private String neighborhood;

    @NotNull(message = "Cidade não pode ser nula")
    @NotBlank(message = "Cidade não pode estar vazia")
    @Schema(description = "Nome da cidade", example = "São Paulo", required = true)
    private String city;

    @NotNull(message = "Estado não pode ser nulo")
    @NotBlank(message = "Estado não pode estar vazio")
    @Schema(description = "Nome do estado", example = "SP", required = true)
    private String state;

    @NotNull(message = "CEP não pode ser nulo")
    @NotBlank(message = "CEP não pode estar vazio")
    @Schema(description = "CEP do endereço", example = "01234-567", required = true)
    private String zipCode;

    @NotNull(message = "ID do agente não pode ser nulo")
    @Schema(description = "ID do agente responsável pelo registro", example = "1", required = true)
    private Long agentId;
}