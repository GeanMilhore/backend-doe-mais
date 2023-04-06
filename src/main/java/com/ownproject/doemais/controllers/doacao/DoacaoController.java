package com.ownproject.doemais.controllers.doacao;

import com.ownproject.doemais.config.security.annotations.HasDoacaoPermission;
import com.ownproject.doemais.controllers.doacao.doacaoValorDto.request.PostDoacaoMonetariaDto;
import com.ownproject.doemais.controllers.doacao.doacaoValorDto.response.DoacaoValorDto;
import com.ownproject.doemais.controllers.doacao.dto.request.PostDoacaoDto;
import com.ownproject.doemais.controllers.doacao.dto.response.DoacaoDto;
import com.ownproject.doemais.services.doacao.DoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @PostMapping("/item")
    @HasDoacaoPermission(value = "realizar_doacao")
    public ResponseEntity<DoacaoDto> realizarDoacao(@RequestBody PostDoacaoDto doacaoDto){
        DoacaoDto responseDoacao = doacaoService.realizarNovaDoacao(doacaoDto);
        return ResponseEntity.ok(responseDoacao);
    }

    @PostMapping("/valor")
    @HasDoacaoPermission(value = "realizar_doacao")
    public ResponseEntity<DoacaoValorDto> realizarDoacaoMonetaria(@RequestBody PostDoacaoMonetariaDto doacaoDto){
        DoacaoValorDto doacaoValorDto = doacaoService.realizarDoacaoMonetaria(doacaoDto);
        return new ResponseEntity<>(doacaoValorDto, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @HasDoacaoPermission(value = "realizar_doacao")
    public ResponseEntity<?> confirmarDoacao(@PathVariable("id") Long idDoacao){
        doacaoService.confirmarDoacao(idDoacao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
