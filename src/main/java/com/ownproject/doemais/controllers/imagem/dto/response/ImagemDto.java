package com.ownproject.doemais.controllers.imagem.dto.response;

import com.ownproject.doemais.domain.imagem.Imagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagemDto {

    public ImagemDto(Imagem imagem){
        this.id = imagem.getId();
        this.nomeCompleto = imagem.getNomeCompleto();
        this.nome = imagem.getNome();
        this.url = imagem.getUrlImagem();
    }

    private Long id;

    private String nomeCompleto;
    private String nome;

    private String url;
}
