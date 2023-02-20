package com.ownproject.doemais.services.organizacao;

import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoEditDto;
import com.ownproject.doemais.controllers.organizacao.dto.request.OrganizacaoPostDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoCreatedDto;
import com.ownproject.doemais.controllers.organizacao.dto.response.OrganizacaoDto;
import com.ownproject.doemais.mappers.organizacao.OrganizacaoMapper;
import com.ownproject.doemais.models.conta.enums.StatusConta;
import com.ownproject.doemais.models.organizacao.Organizacao;
import com.ownproject.doemais.models.organizacao.status.StatusOrganizacao;
import com.ownproject.doemais.repositories.organizacao.OrganizacaoRepository;
import com.ownproject.doemais.services.usuario.UsuarioService;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class OrganizacaoService {

        @Autowired
        private OrganizacaoRepository organizacaoRepository;
        @Autowired
        private UsuarioService usuarioService;
        @Autowired
            private OrganizacaoMapper organizacaoMapper;


        public Organizacao encontrarOrganizacao(Long id) {
            return organizacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Organizacao não encontrada com ID: " + id));
        }

        public OrganizacaoDto detalharOrganizacao(Long id) {
            return organizacaoMapper.toOrganizacaoDto(encontrarOrganizacao(id));
        }

        public List<OrganizacaoDto> detalharTodasOrganizacoes() {
            List<Organizacao> Organizacaos = organizacaoRepository.findAll();
            return organizacaoMapper.allToOrganizacaoDto(Organizacaos);
        }


        @Transactional
        public OrganizacaoCreatedDto criarNovaOrganizacao(OrganizacaoPostDto organizacaoDTO) {
            usuarioService.encontrarUsuario(organizacaoDTO.getIdUsuario());
            Organizacao organizacao = organizacaoMapper.OrganizacaoPostDtoToEntity(organizacaoDTO);
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
