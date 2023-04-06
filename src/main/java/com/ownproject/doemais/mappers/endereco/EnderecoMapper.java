package com.ownproject.doemais.mappers.endereco;


import com.ownproject.doemais.controllers.endereco.dto.request.EnderecoPostDto;
import com.ownproject.doemais.controllers.endereco.dto.response.EnderecoDto;
import com.ownproject.doemais.domain.endereco.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    private ModelMapper modelMapper;

    public EnderecoMapper() {
        modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Endereco.class, EnderecoDto.class)
                .addMapping(endereco -> endereco.getUsuario().getId(), EnderecoDto::setIdUsuario);
    }

    public EnderecoDto enderecoToEnderecoDto(Endereco endereco){
        return modelMapper.map(endereco, EnderecoDto.class);
    }


    public Endereco enderecoPostDtoToEndereco(EnderecoPostDto endereco){
        return modelMapper.map(endereco, Endereco.class);
    }

}
