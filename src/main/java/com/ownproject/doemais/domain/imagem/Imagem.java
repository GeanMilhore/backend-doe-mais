package com.ownproject.doemais.domain.imagem;

import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Imagem(String nome, String nomeCompleto, String urlImagem){
        this.nome = nome;
        this.nomeCompleto = nomeCompleto;
        this.urlImagem = urlImagem;
    }

    private String nome;

    private String nomeCompleto;

    private String urlImagem;

}
