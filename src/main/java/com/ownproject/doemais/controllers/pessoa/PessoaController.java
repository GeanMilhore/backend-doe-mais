package com.ownproject.doemais.controllers.pessoa;

import com.ownproject.doemais.config.security.annotations.HasPessoaPermission;
import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaEditDto;
import com.ownproject.doemais.controllers.pessoa.dto.request.PessoaPostDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaCreatedDto;
import com.ownproject.doemais.controllers.pessoa.dto.response.PessoaDto;
import com.ownproject.doemais.services.pessoa.PessoaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("/api/pessoas")
@SecurityRequirement(name = "bearer-key")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDto> pesquisarPessoa(@PathVariable Long id) {
        PessoaDto pessoaDTO = pessoaService.pesquisarPessoa(id);
        return ResponseEntity.ok(pessoaDTO);
    }

    @GetMapping
    public ResponseEntity<Page<PessoaDto>> pesquisarTodasPessoas(Pageable pageable) {
        return ResponseEntity.ok(pessoaService.pesquisarTodasPessoas(pageable));
    }

    @PostMapping
    @HasPessoaPermission(value="vincular_pessoa")
    public ResponseEntity<PessoaCreatedDto> criarNovaPessoa(@RequestBody @Valid PessoaPostDto pessoaDTO) {
        PessoaCreatedDto createdPessoaDTO = pessoaService.criarNovaPessoa(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPessoaDTO);
    }

    @PutMapping("/{id}")
    @HasPessoaPermission(value="gerenciar_pessoa")
    public ResponseEntity<PessoaDto> editarPessoa(@PathVariable Long id,@RequestBody @Valid PessoaEditDto pessoaDTO) {
        PessoaDto updatedPessoaDTO = pessoaService.editarPessoa(id, pessoaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPessoaDTO);
    }


    @DeleteMapping("/{id}")
    @HasPessoaPermission(value="gerenciar_pessoa")
    public ResponseEntity<?> excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
