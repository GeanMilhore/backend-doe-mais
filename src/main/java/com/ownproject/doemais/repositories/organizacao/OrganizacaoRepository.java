package com.ownproject.doemais.repositories.organizacao;

import com.ownproject.doemais.domain.organizacao.Organizacao;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {

    @Query(value = "select o from Organizacao o where o.usuario.id = ?1 ")
    Optional<Organizacao> findOrganizacaoByUsuarioId(Long id);

    @Override
    Page<Organizacao> findAll(Pageable pageable);
}
