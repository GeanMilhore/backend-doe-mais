package com.ownproject.doemais.mappers.usuario;

import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioDto;
import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioPostDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioCreatedDto;
import com.ownproject.doemais.domain.usuario.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private ModelMapper modelMapper;

    public UserMapper() {
        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Usuario.class, UsuarioCreatedDto.class)
                .addMapping(Usuario::getId, UsuarioCreatedDto::setId)
                .addMapping(Usuario::getEmail, UsuarioCreatedDto::setEmail)
                .addMapping(Usuario::getDataCriacao, UsuarioCreatedDto::setDataCriacao);

        modelMapper.createTypeMap(Usuario.class, UsuarioDto.class)
                .addMapping(Usuario::getId, UsuarioDto::setId)
                .addMapping(usuario -> usuario.getPerfil().getNome(), UsuarioDto::setTipoUsuario)
                .addMapping(Usuario::getEmail, UsuarioDto::setEmail)
                .addMapping(usuario -> usuario.getImagem().getUrlImagem(), UsuarioDto::setUrlImagem);
    }

    public UsuarioCreatedDto usuarioToResponseDto(Usuario Usuario) {
        return modelMapper.map(Usuario, UsuarioCreatedDto.class);
    }

    public Usuario usuarioPostDtoToEntity(UsuarioPostDto UsuarioResponseDto) {
        return modelMapper.map(UsuarioResponseDto, Usuario.class);
    }

    public UsuarioDto toUsuarioDto(Usuario usuarioEncontrado) {
        return modelMapper.map(usuarioEncontrado, UsuarioDto.class);
    }

    public List<UsuarioDto> allToUsuarioDto(List<Usuario> usuarios){
        return usuarios.stream().map(usuario -> toUsuarioDto(usuario)).collect(Collectors.toList());
    }
}
