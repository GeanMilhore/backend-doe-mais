package com.ownproject.doemais.controllers.campanha.dto.response;

import com.ownproject.doemais.controllers.baseRegistro.BaseRegistroDto;
import com.ownproject.doemais.controllers.imagem.dto.response.ImagemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampanhaDto extends BaseRegistroDto {
    private long id;

    private Long idOrganizacao;

    private String nome;

    private String descricao;

    private Long quantidadeItensDoados = 0l;

    private Long valorArrecadado = 0l;

    private String urlImagem;
}
