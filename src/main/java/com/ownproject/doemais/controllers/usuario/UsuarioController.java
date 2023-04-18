package com.ownproject.doemais.controllers.usuario;

import com.ownproject.doemais.config.security.annotations.HasUsuarioPermission;
import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioEditDto;
import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioPostDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioCreatedDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioDto;
import com.ownproject.doemais.services.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioCreatedDto> cadastrarNovoUsuario(@RequestBody @Valid UsuarioPostDto usuario){
        UsuarioCreatedDto usuarioResponse = usuarioService.cadastrarNovoUsuario(usuario);
        return new ResponseEntity<>(usuarioResponse, HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @HasUsuarioPermission(value="gerenciar_usuario")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioDto> editarUsuario(@PathVariable("id") Long idUsuario,
                                                    @RequestBody @Valid UsuarioEditDto usuario){
        UsuarioDto usuarioResponse = usuarioService.editarUsuario(idUsuario, usuario);
        return new ResponseEntity<>(usuarioResponse, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    @HasUsuarioPermission(value="gerenciar_usuario")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UsuarioDto> pesquisarUsuario(@PathVariable("id") Long idUsuario){
        UsuarioDto detalhesUsuario = usuarioService.pesquisarUsuario(idUsuario);
        return new ResponseEntity<>(detalhesUsuario, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping
    @HasUsuarioPermission(value="visualizar_usuarios")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<UsuarioDto>> pesquisarTodosUsuario(Pageable pageable){
        Page<UsuarioDto> detalhesUsuarios = usuarioService.pesquisarTodosUsuario(pageable);
        return new ResponseEntity<>(detalhesUsuarios, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @HasUsuarioPermission(value="gerenciar_usuario")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> excluirUsuario(@RequestParam("id") Long idUsuario){
        usuarioService.excluirUsuario(idUsuario);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
