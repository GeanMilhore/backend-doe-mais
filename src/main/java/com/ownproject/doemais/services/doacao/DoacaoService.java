package com.ownproject.doemais.services.doacao;

import com.ownproject.doemais.controllers.doacao.doacaoValorDto.response.DoacaoValorDto;
import com.ownproject.doemais.controllers.doacao.dto.request.PostDoacaoDto;
import com.ownproject.doemais.controllers.doacao.doacaoValorDto.request.PostDoacaoMonetariaDto;
import com.ownproject.doemais.controllers.doacao.dto.response.DoacaoDto;
import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.doacao.status.StatusDoacao;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import com.ownproject.doemais.domain.doacao.doacaoValor.DoacaoValor;
import com.ownproject.doemais.domain.endereco.Endereco;
import com.ownproject.doemais.mappers.doacao.DoacaoMapper;
import com.ownproject.doemais.mappers.doacaoItem.DoacaoItemMapper;
import com.ownproject.doemais.mappers.doacaoValor.DoacaoValorMapper;
import com.ownproject.doemais.repositories.doacao.DoacaoRepository;
import com.ownproject.doemais.repositories.doacaoValor.DoacaoValorRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.campanha.CampanhaService;
import com.ownproject.doemais.services.endereco.EnderecoService;
import com.ownproject.doemais.services.frete.FreteService;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoacaoService {
    @Autowired
    private DoacaoItemMapper doacaoItemMapper;
    @Autowired
    private DoacaoMapper doacaoMapper;
    @Autowired
    private DoacaoRepository doacaoRepository;
    @Autowired
    private DoacaoValorMapper doacaoValorMapper;
    @Autowired
    private DoacaoValorRepository doacaoValorRepository;
    @Autowired
    private CampanhaService campanhaService;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FreteService freteService;

    public Doacao pesquisarDoacaoPorId(Long id){
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));
    }

    @Transactional
    public void aceitarDoacao(Long idDoacao) {
        Doacao doacaoEncontrada = pesquisarDoacaoPorId(idDoacao);
        doacaoEncontrada.setStatusDoacao(StatusDoacao.ACEITA);
        // TODO - informar ao usuário por email que a doação foi aceita e os próximos passos
        doacaoRepository.save(doacaoEncontrada);
    }

    @Transactional
    public void confirmarEntregaDoacao(Long idDoacao) {
        Doacao doacaoEncontrada = pesquisarDoacaoPorId(idDoacao);
        doacaoEncontrada.setStatusDoacao(StatusDoacao.ENTREGUE);
        // TODO - informar ao usuário por email que a doação chegou a Ong
        doacaoRepository.save(doacaoEncontrada);
    }

    public void recusarDoacao(Long idDoacao) {
        Doacao doacaoEncontrada = pesquisarDoacaoPorId(idDoacao);
        doacaoEncontrada.setStatusDoacao(StatusDoacao.RECUSADA);
        // TODO - informar ao usuário por email que a doação foi recusada
        doacaoRepository.save(doacaoEncontrada);
    }

    @Transactional
    public DoacaoDto realizarNovaDoacao(PostDoacaoDto doacaoDto) {
        Endereco origem = enderecoService.pesquisarEnderecoPorId(doacaoDto.getIdEnderecoOrigem());
        Endereco destino = enderecoService.pesquisarEnderecoPorId(doacaoDto.getIdEnderecoDestino());
        BigDecimal valorFrete = freteService.calcularFrete(origem, destino);

        Doacao novaDocao = new Doacao();
        novaDocao.setCampanha(campanhaService.pesquisarCampanha(doacaoDto.getIdCampanha()));
        novaDocao.setPessoa(tokenService.getPessoaLogada());
        novaDocao.setEnderecoOrigem(origem);
        novaDocao.setEnderecoDestino(destino);
        novaDocao.setStatusDoacao(StatusDoacao.PENDENTE);
        novaDocao.setValorFrete(valorFrete);
        novaDocao.setDataCriacao(DateUtil.dataDeHoje());
        novaDocao.setDataUltimaEdicao(DateUtil.dataDeHoje());
        novaDocao.setItens(mapearDoacaoItens(doacaoDto, novaDocao));
        Doacao doacaoSaved = doacaoRepository.save(novaDocao);
        return doacaoMapper.doacaoToDoacaoDto(doacaoSaved);
    }

    private List<DoacaoItem> mapearDoacaoItens(PostDoacaoDto doacaoDto, Doacao novaDocao) {
        return doacaoDto.getItens()
                .stream()
                .map(dto -> doacaoItemMapper.doacaoItemDtoToDoacaoItem(dto, novaDocao))
                .collect(Collectors.toList());
    }

    @Transactional
    public DoacaoValorDto realizarDoacaoMonetaria(PostDoacaoMonetariaDto doacaoDto) {
        DoacaoValor novaDoacao = new DoacaoValor();
        novaDoacao.setCampanha(campanhaService.pesquisarCampanha(doacaoDto.getIdCampanha()));
        novaDoacao.setValor(doacaoDto.getValor());
        novaDoacao.setPessoa(tokenService.getPessoaLogada());
        DoacaoValor doacaoValor = doacaoValorRepository.save(novaDoacao);
        return doacaoValorMapper.doacaoValorToDoacaoValorDto(doacaoValor);
    }

    public Page<DoacaoDto> pesquisarDoacoesOrganizacaoPorStatus(Long idOng, String status, Pageable pageable) {
        StatusDoacao statusFound = status != null ? StatusDoacao.valueOf(status) : null;
        Page<Doacao> doacoesDaOrganizacao = doacaoRepository
                    .pesquisarDoacoesOrganizacaoPorStatus(statusFound,idOng,pageable);
        return doacoesDaOrganizacao
                .map(doacao -> doacaoMapper.doacaoToDoacaoDto(doacao));
    }

    public Page<DoacaoDto> pesquisarDoacoesPessoaPorStatus(Long idPessoa, String status, Pageable pageable) {
        StatusDoacao statusFound = status != null ? StatusDoacao.valueOf(status) : null;
        Page<Doacao> doacoesDoUsuario = doacaoRepository
                    .pesquisarDoacoesPessoaPorStatus(statusFound, idPessoa, pageable);
        return doacoesDoUsuario
                .map(doacao -> doacaoMapper.doacaoToDoacaoDto(doacao));
    }
}
