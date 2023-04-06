package com.ownproject.doemais.repositories.doacao;

import com.ownproject.doemais.domain.doacao.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {}
