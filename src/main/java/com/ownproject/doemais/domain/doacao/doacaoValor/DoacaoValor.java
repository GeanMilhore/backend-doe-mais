package com.ownproject.doemais.domain.doacao.doacaoValor;

import com.ownproject.doemais.domain.campanha.Campanha;
import com.ownproject.doemais.domain.pessoa.Pessoa;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class DoacaoValor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_campanha")
    private Campanha campanha;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    private BigDecimal valor;
}
