package com.ownproject.doemais.controllers.doacao.dto.response;

import com.ownproject.doemais.controllers.baseRegistro.BaseRegistro;
import com.ownproject.doemais.controllers.doacao.doacaoItemDto.response.DoacaoItemDto;
import com.ownproject.doemais.controllers.endereco.dto.response.EnderecoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoacaoDto extends BaseRegistro {
    private Long id;
    private Long idCampanha;
    private String nomeCampanha;
    private Long idDoador;
    private String doador;
    private EnderecoDto enderecoOrigem;
    private EnderecoDto enderecoDestino;
    private List<DoacaoItemDto> itens;
    private BigDecimal valorFrete;
    private String statusDoacao;
}
