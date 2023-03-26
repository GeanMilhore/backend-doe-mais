package com.ownproject.doemais.controllers.pessoa.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaDto {
    private Long id;
    private Long idUsuario;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String dataCriacao;
    private String dataUltimaEdicao;
    private String dataExclusao;
}
