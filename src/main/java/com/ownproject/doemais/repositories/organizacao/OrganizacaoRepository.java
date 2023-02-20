package com.ownproject.doemais.repositories.organizacao;

import com.ownproject.doemais.models.organizacao.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {}
