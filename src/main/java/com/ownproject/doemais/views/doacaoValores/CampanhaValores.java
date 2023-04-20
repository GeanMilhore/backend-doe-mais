package com.ownproject.doemais.views.doacaoValores;

import com.ownproject.doemais.domain.campanha.Campanha;
import com.ownproject.doemais.domain.organizacao.Organizacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;


@Entity
@Immutable
@Table(name = "campanha_valores")
@Data
public class CampanhaValores {

    @Id
    private Long idCampanha;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campanha", referencedColumnName = "id")
    private Campanha campanha;

    @Column(name = "quantidade_doacoes")
    private Integer quantidadeDoacoes;

    @Column(name = "valor_arrecadado")
    private BigDecimal valorArrecadado;
}
