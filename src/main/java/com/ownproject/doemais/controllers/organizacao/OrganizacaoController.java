package com.ownproject.doemais.controllers.organizacao;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizacoes")
@SecurityRequirement(name = "bearer-key")
public class OrganizacaoController {

    @Autowired
    private OrganizacaoService organizacaoService;


    @GetMapping("/{id}")
    public ResponseEntity<OrganizacaoDto> detalharOrganizacao(@PathVariable Long id) {
        // todo - jwt authentication authenticationService.hasPermission(principal, id);
        OrganizacaoDto organizacaoDTO = organizacaoService.detalharOrganizacao(id);
        return ResponseEntity.ok(organizacaoDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrganizacaoDto>> detalharTodasOrganizacoes() {
        List<OrganizacaoDto> organizacaoDTOList = organizacaoService.detalharTodasOrganizacoes();
        return ResponseEntity.ok(organizacaoDTOList);
    }

    @PostMapping
    public ResponseEntity<OrganizacaoCreatedDto> criarNovaOrganizacao(@RequestBody @Valid OrganizacaoPostDto organizacaoDTO) {
        OrganizacaoCreatedDto createdorganizacaoDTO = organizacaoService.criarNovaOrganizacao(organizacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdorganizacaoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizacaoDto> editarOrganizacao(@PathVariable Long id,
                                                  @RequestBody @Valid OrganizacaoEditDto organizacaoDTO) {
//      todo - jwt authentication  authenticationService.hasPermission(principal, id);
        OrganizacaoDto updatedorganizacaoDTO = organizacaoService.editarOrganizacao(id, organizacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedorganizacaoDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirOrganizacao(@PathVariable Long id) {
//       todo - jwt authentication authenticationService.hasPermission(principal, id);
        organizacaoService.excluirOrganizacao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
