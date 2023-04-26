package com.ownproject.doemais.services.endereco;

import com.ownproject.doemais.controllers.endereco.dto.request.EnderecoEditDto;
import com.ownproject.doemais.controllers.endereco.dto.request.EnderecoPostDto;
import com.ownproject.doemais.controllers.endereco.dto.response.EnderecoDto;
import com.ownproject.doemais.domain.endereco.Endereco;
import com.ownproject.doemais.mappers.endereco.EnderecoMapper;
import com.ownproject.doemais.repositories.endereco.EnderecoRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private TokenService tokenService;

    public Endereco pesquisarEnderecoPorId(Long idEndereco){
        return enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new EntityNotFoundException("Endereço desativo ou inexistente."));
    }

    public EnderecoDto pesquisarEnderecoDtoPorId(Long idEndereco) {
        Endereco endereco = pesquisarEnderecoPorId(idEndereco);
        return enderecoMapper.enderecoToEnderecoDto(endereco);
    }

    @Transactional
    public EnderecoDto cadastrarEndereco(EnderecoPostDto enderecoDto) {
        Endereco endereco = enderecoMapper.enderecoPostDtoToEndereco(enderecoDto);
        endereco.setUsuario(tokenService.getUsuarioLogado());
        endereco.setDataCriacao(DateUtil.dataDeHoje());
        endereco.setDataUltimaEdicao(DateUtil.dataDeHoje());
        return enderecoMapper.enderecoToEnderecoDto(enderecoRepository.save(endereco));
    }

    @Transactional
    public void excluirEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }

    public Page<EnderecoDto> pesquisarEnderecosDto(Pageable pageable) {
        return enderecoRepository
                .findAll(pageable)
                .map(item -> enderecoMapper.enderecoToEnderecoDto(item));
    }

    public EnderecoDto editarEndereco(Long idEndereco, EnderecoEditDto editEnderecoDto) {
        Endereco enderecoEditar = pesquisarEnderecoPorId(idEndereco);
        enderecoEditar.setCep(editEnderecoDto.getCep());
        enderecoEditar.setBairro(editEnderecoDto.getBairro());
        enderecoEditar.setUf(editEnderecoDto.getUf());
        enderecoEditar.setLocalidade(editEnderecoDto.getLocalidade());
        enderecoEditar.setComplemento(editEnderecoDto.getComplemento());
        enderecoEditar.setLogradouro(editEnderecoDto.getLogradouro());
        enderecoEditar.setNumero(editEnderecoDto.getNumero());
        enderecoEditar.setDataUltimaEdicao(DateUtil.dataDeHoje());
        return enderecoMapper.enderecoToEnderecoDto(enderecoRepository.save(enderecoEditar));
    }
}
