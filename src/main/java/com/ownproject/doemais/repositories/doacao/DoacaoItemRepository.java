package com.ownproject.doemais.repositories.doacao;

import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoItemRepository extends JpaRepository<DoacaoItem, Long> {
}
