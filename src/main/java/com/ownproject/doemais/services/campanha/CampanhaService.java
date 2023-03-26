package com.ownproject.doemais.services.campanha;

import com.ownproject.doemais.controllers.campanha.dto.request.CampanhaRequestDto;
import com.ownproject.doemais.controllers.campanha.dto.response.CampanhaDto;
import com.ownproject.doemais.domain.campanha.Campanha;
import com.ownproject.doemais.mappers.campanha.CampanhaMapper;
import com.ownproject.doemais.repositories.campanha.CampanhaRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CampanhaService {

    @Autowired
    private CampanhaMapper campanhaMapper;

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private TokenService tokenService;

    public CampanhaDto pesquisarCampanhaDto(Long id) {
        return campanhaMapper.campanhaToCampanhaDto(pesquisarCampanha(id));
    }

    public Campanha pesquisarCampanha(Long id) {
        return campanhaRepository.findCampanhaById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campanha não encontrada"));
    }

    public Page<CampanhaDto> pesquisarCampanhasDto(Pageable pageable) {
        return campanhaRepository.findAll(pageable).map(campanha -> campanhaMapper.campanhaToCampanhaDto(campanha));
    }

    @Transactional
    public CampanhaDto cadastrarNovaCampanha(CampanhaRequestDto campanhaRequestDto) {
        Campanha campanha = campanhaMapper.campanhaDtoToCampanha(campanhaRequestDto);
        campanha.setOrganizacao(tokenService.getOrganizacaoLogada());
        LocalDateTime hoje = LocalDateTime.now();
        campanha.setDataCriacao(hoje);
        campanha.setDataUltimaEdicao(hoje);
        campanhaRepository.save(campanha);
        return campanhaMapper.campanhaToCampanhaDto(campanha);
    }

    @Transactional
    public CampanhaDto editarCampanha(Long idCampanha, CampanhaRequestDto campanhaRequestDto) {
        Campanha campanhaEncontrada = pesquisarCampanha(idCampanha);
        campanhaEncontrada.setNome(campanhaRequestDto.getNome());
        campanhaEncontrada.setDescricao(campanhaRequestDto.getDescricao());
        campanhaEncontrada.setDataUltimaEdicao(LocalDateTime.now());
        campanhaRepository.save(campanhaEncontrada);
        return campanhaMapper.campanhaToCampanhaDto(campanhaEncontrada);
    }


    @Transactional
    public void excluirCampanha(Long idCampanha){
        Campanha campanhaEncontrada = pesquisarCampanha(idCampanha);
        campanhaEncontrada.setDataExclusao(LocalDateTime.now());
        campanhaRepository.deleteById(idCampanha);
    }
}
