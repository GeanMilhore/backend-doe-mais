package com.ownproject.doemais.services.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavedFileInformation {

    private Long idImagem;
    private String fileName;
    private String fileUrl;
}
