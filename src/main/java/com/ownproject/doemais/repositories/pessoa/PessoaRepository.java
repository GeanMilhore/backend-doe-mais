package com.ownproject.doemais.repositories.pessoa;

import com.ownproject.doemais.domain.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {}
