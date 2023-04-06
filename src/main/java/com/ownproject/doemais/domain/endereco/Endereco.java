package com.ownproject.doemais.domain.endereco;

import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private Long numero;

    @ManyToOne
    @JoinTable(
            name = "usuario_endereco",
            joinColumns = @JoinColumn(name = "endereco_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Usuario usuario;
}
