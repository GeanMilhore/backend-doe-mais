package com.ownproject.doemais.controllers.endereco;

import com.ownproject.doemais.config.security.annotations.HasEnderecoPermission;
import com.ownproject.doemais.controllers.endereco.dto.request.EnderecoEditDto;
import com.ownproject.doemais.controllers.endereco.dto.request.EnderecoPostDto;
import com.ownproject.doemais.controllers.endereco.dto.response.EnderecoDto;
import com.ownproject.doemais.services.endereco.EnderecoService;
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
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDto> detalharEndereco(@PathVariable("id") Long idEndereco){
        return new ResponseEntity<>(enderecoService.pesquisarEnderecoDtoPorId(idEndereco), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EnderecoDto>> detalharEnderecos(Pageable pageable){
        return new ResponseEntity<>(enderecoService.pesquisarEnderecosDto(pageable), HttpStatus.OK);
    }

    @PostMapping
    @HasEnderecoPermission("vincular_endereco")
    public ResponseEntity<EnderecoDto> cadastrarEndereco(@RequestBody @Valid EnderecoPostDto enderecoDto){
        EnderecoDto responseDto = enderecoService.cadastrarEndereco(enderecoDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @HasEnderecoPermission("gerenciar_endereco")
    public ResponseEntity<EnderecoDto> editarEndereco(@PathVariable("id") Long idEndereco,
                                                      @RequestBody @Valid EnderecoEditDto editEnderecoDto){
        return new ResponseEntity<>(enderecoService.editarEndereco(idEndereco, editEnderecoDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @HasEnderecoPermission("gerenciar_endereco")
    public ResponseEntity<Void> excluirEndereco(@PathVariable("id") Long idEndereco){
        enderecoService.excluirEndereco(idEndereco);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
