package com.ownproject.doemais.mappers.usuario;

import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioDto;
import com.ownproject.doemais.controllers.usuario.dto.request.UsuarioPostDto;
import com.ownproject.doemais.controllers.usuario.dto.response.UsuarioCreatedDto;
import com.ownproject.doemais.models.usuario.Usuario;
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
                .addMapping(Usuario::getNome, UsuarioCreatedDto::setNome)
                .addMapping(Usuario::getEmail, UsuarioCreatedDto::setEmail)
                .addMapping(Usuario::getDataCriacao, UsuarioCreatedDto::setDataCriacao);
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
