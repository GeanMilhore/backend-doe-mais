package com.ownproject.doemais.repositories.doacao;

import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.doacao.status.StatusDoacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    @Query(value = "select d from Doacao d where (:status is null or d.statusDoacao = :status) and d.campanha.organizacao.id = :idOrganizacao ")
    Page<Doacao> pesquisarDoacoesOrganizacaoPorStatus(@Param("status") StatusDoacao status, @Param("idOrganizacao") Long idOrganizacao, Pageable pageable);

    @Query(value = "select d from Doacao d where (:status is null or d.statusDoacao = :status) and d.pessoa.id = :idPessoa ")
    Page<Doacao> pesquisarDoacoesPessoaPorStatus(@Param("status") StatusDoacao status, @Param("idPessoa") Long idPessoa, Pageable pageable);
}
