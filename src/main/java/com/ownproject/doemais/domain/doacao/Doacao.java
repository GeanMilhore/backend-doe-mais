package com.ownproject.doemais.domain.doacao;

import com.ownproject.doemais.controllers.baseRegistro.BaseRegistro;
import com.ownproject.doemais.domain.campanha.Campanha;
import com.ownproject.doemais.domain.doacao.status.StatusDoacao;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import com.ownproject.doemais.domain.endereco.Endereco;
import com.ownproject.doemais.domain.pessoa.Pessoa;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Doacao extends BaseRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_campanha")
    private Campanha campanha;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "id_endereco_origem")
    private Endereco enderecoOrigem;

    @ManyToOne
    @JoinColumn(name = "id_endereco_destino")
    private Endereco enderecoDestino;

    @OneToMany(mappedBy = "doacao")
    @Cascade(CascadeType.ALL)
    private List<DoacaoItem> itens;

    private BigDecimal valorFrete;

    @Enumerated
    private StatusDoacao statusDoacao;
}


