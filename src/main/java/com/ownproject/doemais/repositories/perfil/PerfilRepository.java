package com.ownproject.doemais.repositories.perfil;

import com.ownproject.doemais.domain.perfil.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findPerfilById(Long id);
}
