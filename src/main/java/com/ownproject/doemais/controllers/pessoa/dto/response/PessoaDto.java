package com.ownproject.doemais.controllers.pessoa.dto.response;

import com.ownproject.doemais.controllers.baseConta.BaseContaDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PessoaDto extends BaseContaDto {

    private Long id;

    private Long idUsuario;

    private String nome;

    private String cpf;

    private Date dataNascimento;


}
