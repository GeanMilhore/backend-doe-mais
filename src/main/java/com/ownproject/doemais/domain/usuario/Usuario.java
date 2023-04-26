package com.ownproject.doemais.domain.usuario;

import com.ownproject.doemais.domain.conta.BaseConta;
import com.ownproject.doemais.domain.endereco.Endereco;
import com.ownproject.doemais.domain.imagem.Imagem;
import com.ownproject.doemais.domain.perfil.Perfil;
import com.ownproject.doemais.interfaces.imagem.ImagemIlustravel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends BaseConta implements UserDetails, ImagemIlustravel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_usuario")
    @Cascade(CascadeType.ALL)
    private Perfil perfil;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<Endereco> enderecos;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinTable(
            name = "usuario_imagem",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "imagem_id")
    )
    private Imagem imagem;

    private String email;

    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfil.getAutorizacoes();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getStatus().equals("BLOQUEADO");
    }

    @Override
    public boolean isEnabled() {
        return !getStatus().equals("INATIVO");
    }

}
