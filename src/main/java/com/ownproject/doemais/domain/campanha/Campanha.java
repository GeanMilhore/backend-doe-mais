package com.ownproject.doemais.domain.campanha;

import com.ownproject.doemais.controllers.baseRegistro.BaseRegistro;
import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.organizacao.Organizacao;
import com.ownproject.doemais.views.doacaoValores.CampanhaValores;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Entity(name = "campanha")
@Data
public class Campanha extends BaseRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_organizacao")
    private Organizacao organizacao;

    @OneToMany(mappedBy = "campanha")
    private List<Doacao> doacoes;

    @OneToOne(mappedBy = "campanha")
    private CampanhaValores valores;

    private String nome;

    private String descricao;

    private Long quantidadeItensDoados = 0l;

    private Long valorArrecadado = 0l;
}
