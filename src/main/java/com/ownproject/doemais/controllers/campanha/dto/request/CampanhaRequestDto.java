package com.ownproject.doemais.controllers.campanha.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampanhaRequestDto {

    @NotEmpty
    private String nome;

    @NotEmpty
    private String descricao;

}