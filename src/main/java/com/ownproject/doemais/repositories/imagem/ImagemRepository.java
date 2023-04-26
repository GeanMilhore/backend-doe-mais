package com.ownproject.doemais.repositories.imagem;

import com.ownproject.doemais.domain.imagem.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {}