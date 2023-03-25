package com.ownproject.doemais.services.usuario;

import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioEditDto;
import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioPostDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioCreatedDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioDto;
import com.ownproject.doemais.domain.conta.enums.StatusConta;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.mappers.usuario.UserMapper;
import com.ownproject.doemais.repositories.usuario.UsuarioRepository;
import com.ownproject.doemais.services.password.PasswordService;
import com.ownproject.doemais.services.perfil.PerfilService;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Service
public class UsuarioService {

    @Autowired
    PasswordService passwordService;
    @Autowired
    PerfilService perfilService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public UsuarioCreatedDto cadastrarNovoUsuario(UsuarioPostDto usuarioPostDto) {
        Usuario novoUsuario = userMapper.usuarioPostDtoToEntity(usuarioPostDto);
        novoUsuario.setStatus(StatusConta.ATIVO);
        LocalDateTime hoje = DateUtil.dataDeHoje();
        novoUsuario.setDataCriacao(hoje);
        novoUsuario.setDataUltimaEdicao(hoje);
        novoUsuario.setSenha(passwordService.encriptarSenhaUsuario(novoUsuario));
        novoUsuario.setPerfil(perfilService.pesquisarPerfilPorId(usuarioPostDto.getIdPerfil()));
        Usuario usuarioSaved = usuarioRepository.save(novoUsuario);
        return userMapper.usuarioToResponseDto(usuarioSaved);
    }

    @Transactional
    public void excluirUsuario(Long idUsuario) {
        encontrarUsuario(idUsuario);
        usuarioRepository.deleteById(idUsuario);
    }

    public Usuario encontrarUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Usuario encontrarUsuarioPeloEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public UsuarioDto pesquisarUsuario(Long idUsuario) {
        return userMapper.toUsuarioDto(encontrarUsuario(idUsuario));
    }

    @Transactional
    public UsuarioDto editarUsuario(Long idUsuario, UsuarioEditDto usuarioEditDto) {
        Usuario usuarioOriginal = encontrarUsuario(idUsuario);
        usuarioOriginal.setEmail(usuarioEditDto.getEmail());
        return userMapper.toUsuarioDto(usuarioRepository.save(usuarioOriginal));
    }

    public Page<UsuarioDto> pesquisarTodosUsuario(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(userMapper::toUsuarioDto);
    }
}
