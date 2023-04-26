package com.ownproject.doemais.domain.passwordToken;


import com.ownproject.doemais.domain.passwordToken.status.StatusToken;
import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailUsuario;

    private String tokenPassword;

    private Long tempoExpiracao;

    @Enumerated(EnumType.STRING)
    private StatusToken status;

    private LocalDateTime dataCriacao;

    public Boolean estaEspirado(){
        if(System.currentTimeMillis() > this.tempoExpiracao){
            setStatus(StatusToken.EXPIRADO);
            return true;
        }
        return false;
    }

    public Boolean estaAtivo(){
        return this.status == StatusToken.ATIVO;
    }
}


