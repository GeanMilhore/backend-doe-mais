package com.ownproject.doemais.repositories.doacaoValor;

import com.ownproject.doemais.domain.doacao.doacaoValor.DoacaoValor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoValorRepository extends JpaRepository<DoacaoValor, Long> { }
