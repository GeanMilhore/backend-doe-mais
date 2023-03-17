package com.ownproject.doemais.domain.autorizacao;

import com.ownproject.doemais.domain.perfil.Perfil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Entity
public class Autorizacao implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "autorizacoes")
    private List<Perfil> perfis;

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
