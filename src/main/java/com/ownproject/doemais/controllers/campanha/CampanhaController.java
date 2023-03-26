package com.ownproject.doemais.controllers.campanha;

import com.ownproject.doemais.config.security.annotations.HasCampanhaPermission;
import com.ownproject.doemais.controllers.campanha.dto.request.CampanhaRequestDto;
import com.ownproject.doemais.controllers.campanha.dto.response.CampanhaDto;
import com.ownproject.doemais.services.campanha.CampanhaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/campanhas")
@SecurityRequirement(name = "bearer-key")
public class CampanhaController {

    @Autowired
    private CampanhaService campanhaService;

    @GetMapping
    public ResponseEntity<Page<CampanhaDto>> detalharCampanhas(Pageable pageable){
        return new ResponseEntity<>(campanhaService.pesquisarCampanhasDto(pageable),
                HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampanhaDto> detalharCampanha(@PathVariable("id") Long id){
        CampanhaDto detalhesCampanha = campanhaService.pesquisarCampanhaDto(id);
        return new ResponseEntity<>(detalhesCampanha, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<CampanhaDto> cadastrarCampanha(@RequestBody CampanhaRequestDto campanhaRequestDto){
        return new ResponseEntity<>(campanhaService
                .cadastrarNovaCampanha(campanhaRequestDto), HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }
    @PutMapping("/{id}")
    @HasCampanhaPermission(value = "gerenciar_campanha")
    public ResponseEntity<CampanhaDto> editarCampanha(@PathVariable("id") Long idCampanha, @RequestBody CampanhaRequestDto campanhaRequestDto){
        return new ResponseEntity<>(campanhaService
                .editarCampanha(idCampanha, campanhaRequestDto), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @HasCampanhaPermission(value = "gerenciar_campanha")
    public ResponseEntity<Void> editarCampanha(@PathVariable("id") Long idCampanha){
        campanhaService.excluirCampanha(idCampanha);
        return new ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
    }
}
