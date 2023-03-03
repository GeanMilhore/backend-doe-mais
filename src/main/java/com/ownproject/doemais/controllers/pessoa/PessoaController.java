package com.ownproject.doemais.controllers.pessoa;

import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaEditDto;
import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaPostDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaCreatedDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaDto;
import com.ownproject.doemais.services.pessoa.PessoaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@SecurityRequirement(name = "bearer-key")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDto> detalharPessoa(@PathVariable Long id) {
        // todo - jwt authentication authenticationService.hasPermission(principal, id);
        PessoaDto pessoaDTO = pessoaService.detalharPessoa(id);
        return ResponseEntity.ok(pessoaDTO);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDto>> detalharTodasPessoas() {
        List<PessoaDto> pessoaDTOList = pessoaService.detalharTodasPessoas();
        return ResponseEntity.ok(pessoaDTOList);
    }

    @PostMapping
    public ResponseEntity<PessoaCreatedDto> criarNovaPessoa(@RequestBody @Valid PessoaPostDto pessoaDTO) {
        PessoaCreatedDto createdPessoaDTO = pessoaService.criarNovaPessoa(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPessoaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDto> editarPessoa(@PathVariable Long id,
                                                  @RequestBody @Valid PessoaEditDto pessoaDTO,
                                                  Principal principal) {
        PessoaDto updatedPessoaDTO = pessoaService.editarPessoa(id, pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedPessoaDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPessoa(@PathVariable Long id) {
//       todo - jwt authentication authenticationService.hasPermission(principal, id);
        pessoaService.excluirPessoa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
