package com.ownproject.doemais.controllers.organizacao;

import com.ownproject.doemais.config.security.annotations.HasOrganizacaoPermission;
import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoEditDto;
import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoPostDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoCreatedDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoDto;
import com.ownproject.doemais.services.organizacao.OrganizacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizacoes")
@SecurityRequirement(name = "bearer-key")
public class OrganizacaoController {

    @Autowired
    private OrganizacaoService organizacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<OrganizacaoDto> detalharOrganizacao(@PathVariable Long id) {
        OrganizacaoDto organizacaoDTO = organizacaoService.detalharOrganizacao(id);
        return ResponseEntity.ok(organizacaoDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrganizacaoDto>> detalharTodasOrganizacoes() {
        List<OrganizacaoDto> organizacaoDTOList = organizacaoService.detalharTodasOrganizacoes();
        return ResponseEntity.ok(organizacaoDTOList);
    }

    @PostMapping
    @HasOrganizacaoPermission(value="vincular_organizacao")
    public ResponseEntity<OrganizacaoCreatedDto> criarNovaOrganizacao(@RequestBody @Valid OrganizacaoPostDto organizacaoDTO) {
        OrganizacaoCreatedDto createdorganizacaoDTO = organizacaoService.criarNovaOrganizacao(organizacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdorganizacaoDTO);
    }

    @PutMapping("/{id}")
    @HasOrganizacaoPermission(value = "gerenciar_organizacao")
    public ResponseEntity<OrganizacaoDto> editarOrganizacao(@PathVariable Long id,
                                                  @RequestBody @Valid OrganizacaoEditDto organizacaoDTO) {
        OrganizacaoDto updatedorganizacaoDTO = organizacaoService.editarOrganizacao(id, organizacaoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedorganizacaoDTO);
    }

    @DeleteMapping("/{id}")
    @HasOrganizacaoPermission(value = "gerenciar_organizacao")
    public ResponseEntity<?> excluirOrganizacao(@PathVariable Long id) {
        organizacaoService.excluirOrganizacao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
