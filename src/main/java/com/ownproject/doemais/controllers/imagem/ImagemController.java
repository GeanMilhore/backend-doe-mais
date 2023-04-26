package com.ownproject.doemais.controllers.imagem;

import com.ownproject.doemais.config.security.annotations.HasCampanhaPermission;
import com.ownproject.doemais.config.security.annotations.HasDoacaoPermission;
import com.ownproject.doemais.config.security.annotations.HasUsuarioPermission;
import com.ownproject.doemais.controllers.imagem.dto.response.ImagemDto;
import com.ownproject.doemais.services.imagem.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/imagem")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping("/usuario")
    @HasUsuarioPermission("vincular_imagem_usuario")
    public ResponseEntity<ImagemDto> vincularImagemUsuario(@RequestParam(value = "file") MultipartFile file){
        return new ResponseEntity<>(imagemService.vincularImagemUsuario(file), HttpStatus.OK);
    }

    @DeleteMapping("/usuario")
    @HasUsuarioPermission("remover_imagem_usuario")
    public ResponseEntity<Void> removerImagemUsuario(){
        imagemService.removerImagemUsuario();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/campanha/{id}")
    @HasCampanhaPermission("gerenciar_imagem_campanha")
    public ResponseEntity<ImagemDto> vincularImagemCampanha(@PathVariable("id") Long idCampanha,
                                                        @RequestParam(value = "file") MultipartFile file){
        return new ResponseEntity<>(imagemService.vincularImagemCampanha(idCampanha, file), HttpStatus.OK);
    }

    @DeleteMapping("/campanha/{id}")
    @HasCampanhaPermission("gerenciar_imagem_campanha")
    public ResponseEntity<Void> removerImagemCampanha(@PathVariable("id") Long idCampanha){
        imagemService.removerImagemCampanha(idCampanha);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/doacao/{id}")
    @HasDoacaoPermission("gerenciar_imagem_doacao")
    public ResponseEntity<ImagemDto> vincularImagemDoacao(@PathVariable("id") Long idDoacao,
                                                        @RequestParam(value = "file") MultipartFile file){
        return new ResponseEntity<>(imagemService.vincularImagemDoacao(idDoacao, file), HttpStatus.OK);
    }

    @DeleteMapping("/doacao/{id}")
    @HasDoacaoPermission("gerenciar_imagem_doacao")
    public ResponseEntity<Void> removerImagemDoacao(@PathVariable("id") Long idDoacao){
        imagemService.removerImagemDoacao(idDoacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
