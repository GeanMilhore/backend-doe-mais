package com.ownproject.doemais.services.usuario;

import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioEditDto;
import com.ownproject.doemais.models.conta.enums.StatusConta;
import com.ownproject.doemais.exception.handlers.DomainException;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioDto;
import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioPostDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioCreatedDto;
import com.ownproject.doemais.models.usuario.enums.TipoUsuario;
import com.ownproject.doemais.mappers.usuario.UserMapper;
import com.ownproject.doemais.models.usuario.Usuario;
import com.ownproject.doemais.repositories.usuario.UsuarioRepository;
import com.ownproject.doemais.utils.data.DateUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UserMapper userMapper;

    public UsuarioCreatedDto cadastrarNovoUsuario(UsuarioPostDto usuarioPostDto) {
        Usuario novoUsuario = userMapper.usuarioPostDtoToEntity(usuarioPostDto);
        novoUsuario.setStatus(StatusConta.ATIVO);
        novoUsuario.setTipoUsuario(pegarTipoUsuarioEnum(usuarioPostDto));
        LocalDateTime hoje = DateUtil.dataDeHoje();
        novoUsuario.setDataCriacao(hoje);
        novoUsuario.setDataUltimaEdicao(hoje);
        Usuario usuarioSaved = usuarioRepository.save(novoUsuario);
        return userMapper.usuarioToResponseDto(usuarioSaved);
    }

    private TipoUsuario pegarTipoUsuarioEnum(UsuarioPostDto usuarioPostDto) {
        return TipoUsuario.pegarTipoUsuario(usuarioPostDto.getTipoUsuario())
                .orElseThrow(() -> new DomainException("Tipo de usuário inexistente."));
    }

    public void excluirUsuario(Long idUsuario) {
        encontrarUsuario(idUsuario);
        usuarioRepository.deleteById(idUsuario);
    }

    public Usuario encontrarUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new DomainException("Usuário não encontrado"));
    }

    public UsuarioDto detalharUsuario(Long idUsuario) {
        return userMapper.toUsuarioDto(encontrarUsuario(idUsuario));
    }

    public UsuarioDto editarUsuario(Long idUsuario, UsuarioEditDto usuarioEditDto) {
        Usuario usuarioOriginal = encontrarUsuario(idUsuario);
        usuarioOriginal.setNome(usuarioEditDto.getNome());
        return userMapper.toUsuarioDto(usuarioRepository.save(usuarioOriginal));
    }

    public List<UsuarioDto> detalharTodosUsuario() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return userMapper.allToUsuarioDto(usuarios);
    }
}
