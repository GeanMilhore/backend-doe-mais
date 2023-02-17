package com.ownproject.doemais.controllers.usuario;

import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioEditDto;
import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioPostDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioCreatedDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioDto;
import com.ownproject.doemais.services.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioCreatedDto> cadastrarNovoUsuario(@RequestBody @Valid UsuarioPostDto usuario){
        UsuarioCreatedDto usuarioResponse = usuarioService.cadastrarNovoUsuario(usuario);
        return new ResponseEntity<>(usuarioResponse, HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<UsuarioDto> editarUsuario(@RequestParam("id") Long idUsuario,
                                                    @RequestBody @Valid UsuarioEditDto usuario){
        UsuarioDto usuarioResponse = usuarioService.editarUsuario(idUsuario, usuario);
        return new ResponseEntity<>(usuarioResponse, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirUsuario(@RequestParam("id") Long idUsuario){
        usuarioService.excluirUsuario(idUsuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> detalharUsuario(@RequestParam("id") Long idUsuario){
        UsuarioDto detalhesUsuario = usuarioService.detalharUsuario(idUsuario);
        return new ResponseEntity<>(detalhesUsuario, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDto>> detalharTodosUsuario(){
        List<UsuarioDto> detalhesUsuarios = usuarioService.detalharTodosUsuario();
        return new ResponseEntity<>(detalhesUsuarios, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
