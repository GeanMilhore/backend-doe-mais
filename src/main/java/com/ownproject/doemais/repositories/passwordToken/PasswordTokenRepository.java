package com.ownproject.doemais.repositories.passwordToken;

import com.ownproject.doemais.domain.passwordToken.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

    @Query(value = "select pt from PasswordToken pt where pt.tokenPassword = :token ")
    Optional<PasswordToken> pesquisarPasswordTokenPorToken(@Param("token") String token);
}
