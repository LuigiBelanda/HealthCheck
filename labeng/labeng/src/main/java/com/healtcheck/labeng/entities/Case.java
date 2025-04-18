package com.healtcheck.labeng.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String disease;

    // Endereço separado em campos específicos
    @Column(nullable = false)
    private String street; // Rua/Avenida

    @Column(nullable = false)
    private String number; // Número

    @Column
    private String complement; // Complemento (opcional)

    @Column(nullable = false)
    private String neighborhood; // Bairro

    @Column(nullable = false)
    private String city; // Cidade

    @Column(nullable = false)
    private String state; // Estado

    @Column(nullable = false)
    private String zipCode; // CEP

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column
    private String longitude;

    @Column
    private String latitude;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;
}
