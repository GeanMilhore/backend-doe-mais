package com.ownproject.doemais.repositories.campanha;

import com.ownproject.doemais.domain.campanha.Campanha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {
    Optional<Campanha> findCampanhaById(Long id);
}
