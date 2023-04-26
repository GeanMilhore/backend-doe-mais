package com.ownproject.doemais.domain.doacao.doacaoItem;

import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.imagem.Imagem;
import com.ownproject.doemais.interfaces.imagem.ImagemIlustravel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Data
@Entity
public class DoacaoItem implements ImagemIlustravel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @JoinTable(
            name = "doacao_item_imagem",
            joinColumns = @JoinColumn(name = "doacao_item_id"),
            inverseJoinColumns = @JoinColumn(name = "imagem_id")
    )
    private Imagem imagem;
    @ManyToOne
    @JoinColumn(name = "id_doacao")
    private Doacao doacao;
}
