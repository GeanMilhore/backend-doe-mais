package com.ownproject.doemais.models.organizacao;

import com.ownproject.doemais.models.conta.BaseConta;
import com.ownproject.doemais.models.organizacao.status.StatusOrganizacao;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Long idUsuario;

    private String cnpj;

    @Enumerated(EnumType.STRING)
    private StatusOrganizacao statusOrganizacao;

    private String razaoSocial;

    private String nomeResponsavel;

    private Long idadeInstituicao;
}
