package com.ownproject.doemais.repositories.pessoa;

import com.ownproject.doemais.domain.pessoa.Pessoa;
import com.ownproject.doemais.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("select p from Pessoa p where p.usuario.id = ?1 ")
    Optional<Pessoa> findPessoaByIdUsuario(Long idUsuario);
}
