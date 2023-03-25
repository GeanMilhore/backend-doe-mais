package com.ownproject.doemais.services.organizacao;

import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoEditDto;
import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoPostDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoCreatedDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoDto;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.mappers.organizacao.OrganizacaoMapper;
import com.ownproject.doemais.domain.conta.enums.StatusConta;
import com.ownproject.doemais.domain.organizacao.Organizacao;
import com.ownproject.doemais.domain.organizacao.status.StatusOrganizacao;
import com.ownproject.doemais.repositories.organizacao.OrganizacaoRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.usuario.UsuarioService;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizacaoService {

        @Autowired
        private OrganizacaoRepository organizacaoRepository;
        @Autowired
        private TokenService tokenService;
        @Autowired
        private OrganizacaoMapper organizacaoMapper;


        public Organizacao encontrarOrganizacao(Long id) {
            return organizacaoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Organizacao não encontrada com ID: " + id));
        }

        public OrganizacaoDto pesquisarOrganizacao(Long id) {
            return organizacaoMapper.toOrganizacaoDto(encontrarOrganizacao(id));
        }

        public Page<OrganizacaoDto> pesquisarTodasOrganizacoes(Pageable pageable) {
            return organizacaoRepository.findAll(pageable)
                    .map(organizacao -> organizacaoMapper.toOrganizacaoDto(organizacao));
        }

        public Organizacao pesquisarOrganizacaoPorIdUsuario(Usuario usuario){
            return organizacaoRepository.findOrganizacaoByUsuarioId(usuario.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não possui organização vínculada. "));
        }


        @Transactional
        public OrganizacaoCreatedDto criarNovaOrganizacao(OrganizacaoPostDto organizacaoDTO) {
            Organizacao organizacao = organizacaoMapper.OrganizacaoPostDtoToEntity(organizacaoDTO);
            organizacao.setUsuario(tokenService.getUsuarioLogado());
            organizacao.setStatus(StatusConta.ATIVO);
            organizacao.setStatusOrganizacao(StatusOrganizacao.EM_ANALISE);
            organizacao.setDataCriacao(DateUtil.dataDeHoje());
            organizacao.setDataUltimaEdicao(DateUtil.dataDeHoje());
            organizacao = organizacaoRepository.save(organizacao);
            return organizacaoMapper.OrganizacaoToCreatedDto(organizacao);
        }

        @Transactional
        public OrganizacaoDto editarOrganizacao(Long id, OrganizacaoEditDto OrganizacaoDTO) {
            Organizacao OrganizacaoOriginal = encontrarOrganizacao(id);
            organizacaoMapper.atualizarOrganizacaoPeloDto(OrganizacaoDTO, OrganizacaoOriginal);
            OrganizacaoOriginal.setDataUltimaEdicao(DateUtil.dataDeHoje());
            Organizacao OrganizacaoUpdated = organizacaoRepository.save(OrganizacaoOriginal);
            return organizacaoMapper.toOrganizacaoDto(OrganizacaoUpdated);
        }


        @Transactional
        public void excluirOrganizacao(Long id) {
            Organizacao Organizacao = encontrarOrganizacao(id);
            organizacaoRepository.delete(Organizacao);
        }
}
