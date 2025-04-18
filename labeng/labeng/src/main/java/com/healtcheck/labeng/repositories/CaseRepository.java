package com.healtcheck.labeng.repositories;

import com.healtcheck.labeng.entities.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Long> {
    // Busca por cidade
    List<Case> findByCity(String city);

    // Busca por cidade e bairro
    List<Case> findByCityAndNeighborhood(String city, String neighborhood);

    // Busca por cidade e doença
    List<Case> findByCityAndDisease(String city, String disease);

    // Busca por cidade, bairro e doença
    List<Case> findByCityAndNeighborhoodAndDisease(String city, String neighborhood, String disease);
}
