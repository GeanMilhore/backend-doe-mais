package com.ownproject.doemais.controllers.doacao;

import com.ownproject.doemais.config.security.annotations.HasDoacaoPermission;
import com.ownproject.doemais.controllers.doacao.doacaoValorDto.request.PostDoacaoMonetariaDto;
import com.ownproject.doemais.controllers.doacao.doacaoValorDto.response.DoacaoValorDto;
import com.ownproject.doemais.controllers.doacao.dto.request.PostDoacaoDto;
import com.ownproject.doemais.controllers.doacao.dto.response.DoacaoDto;
import com.ownproject.doemais.services.doacao.DoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/aceitar/{id}")
    @HasDoacaoPermission(value = "aceitar_doacao")
    public ResponseEntity<?> aceitarDoacao(@PathVariable("id") Long idDoacao){
        doacaoService.aceitarDoacao(idDoacao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/confirmar/{id}")
    @HasDoacaoPermission(value = "confirmar_entrega_doacao")
    public ResponseEntity<?> confirmarEntregaDoacao(@PathVariable("id") Long idDoacao){
        doacaoService.confirmarEntregaDoacao(idDoacao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/recusar/{id}")
    @HasDoacaoPermission(value = "recusar_doacao")
    public ResponseEntity<?> recusarDoacao(@PathVariable("id") Long idDoacao){
        doacaoService.recusarDoacao(idDoacao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/organizacao/{idOng}")
    @HasDoacaoPermission(value = "visualizar_doacoes_organizacao")
    public ResponseEntity<Page<DoacaoDto>> visualizarDoacoesOrganizacao(@PathVariable("idOng") Long idOng,
                                                                        @RequestParam(value = "status", required = false) String status,
                                                                        Pageable pageable){
        Page<DoacaoDto> doacoes = doacaoService.pesquisarDoacoesOrganizacaoPorStatus(idOng, status, pageable);
        return new ResponseEntity<>(doacoes, HttpStatus.OK);
    }

    @GetMapping("/pessoa/{idPessoa}")
    @HasDoacaoPermission(value = "visualizar_doacoes_pessoa")
    public ResponseEntity<Page<DoacaoDto>> visualizarDoacoesPessoa(@PathVariable("idPessoa") Long idPessoa,
                                                                   @RequestParam(value = "status", required = false) String status,
                                                                   Pageable pageable){
        Page<DoacaoDto> doacoes = doacaoService.pesquisarDoacoesPessoaPorStatus(idPessoa, status, pageable);
        return new ResponseEntity<>(doacoes, HttpStatus.OK);
    }
}
