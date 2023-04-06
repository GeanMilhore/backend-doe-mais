package com.ownproject.doemais.domain.doacao.doacaoItem;

import com.ownproject.doemais.domain.doacao.Doacao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DoacaoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "id_doacao")
    private Doacao doacao;
}
