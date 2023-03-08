package com.ownproject.doemais.domain.organizacao;

import com.ownproject.doemais.domain.conta.BaseConta;
import com.ownproject.doemais.domain.organizacao.status.StatusOrganizacao;
import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organizacao extends BaseConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nome;

    private String cnpj;

    @Enumerated(EnumType.STRING)
    private StatusOrganizacao statusOrganizacao;

    private String razaoSocial;

    private String nomeResponsavel;

    private Long idadeInstituicao;
}
