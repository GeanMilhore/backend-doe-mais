package com.ownproject.doemais.services.doacao;

import com.ownproject.doemais.controllers.doacao.doacaoValorDto.response.DoacaoValorDto;
import com.ownproject.doemais.controllers.doacao.dto.request.PostDoacaoDto;
import com.ownproject.doemais.controllers.doacao.doacaoValorDto.request.PostDoacaoMonetariaDto;
import com.ownproject.doemais.controllers.doacao.dto.response.DoacaoDto;
import com.ownproject.doemais.domain.doacao.Doacao;
import com.ownproject.doemais.domain.doacao.status.StatusDoacao;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import com.ownproject.doemais.domain.doacao.doacaoValor.DoacaoValor;
import com.ownproject.doemais.domain.email.EmailModel;
import com.ownproject.doemais.domain.endereco.Endereco;
import com.ownproject.doemais.domain.organizacao.Organizacao;
import com.ownproject.doemais.mappers.doacao.DoacaoMapper;
import com.ownproject.doemais.mappers.doacaoItem.DoacaoItemMapper;
import com.ownproject.doemais.mappers.doacaoValor.DoacaoValorMapper;
import com.ownproject.doemais.repositories.doacao.DoacaoRepository;
import com.ownproject.doemais.repositories.doacaoValor.DoacaoValorRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.campanha.CampanhaService;
import com.ownproject.doemais.services.email.EmailService;
import com.ownproject.doemais.services.endereco.EnderecoService;
import com.ownproject.doemais.services.frete.FreteService;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
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
    @Autowired
    private EmailService emailService;

    @Value("${projeto.notificacoes.email}")
    private String emailProjeto;
    @Value("${projeto.notificacoes.doacao.aceita}")
    private String mensagemAceite;
    @Value("${projeto.notificacoes.doacao.confirmada}")
    private String mensagemConfirma;
    @Value("${projeto.notificacoes.doacao.recusada}")
    private String mensagemRecusa;


    public Doacao pesquisarDoacaoPorId(Long id){
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));
    }

    @Transactional
    public void aceitarDoacao(Long idDoacao) {
        Doacao doacaoEncontrada = pesquisarDoacaoPorId(idDoacao);
        doacaoEncontrada.setStatusDoacao(StatusDoacao.ACEITA);
        Doacao doacao = doacaoRepository.save(doacaoEncontrada);
        enviarEmailDeAtualizacaoStatusDoacao(doacao, mensagemAceite);
    }

    @Transactional
    public void confirmarEntregaDoacao(Long idDoacao) {
        Doacao doacaoEncontrada = pesquisarDoacaoPorId(idDoacao);
        doacaoEncontrada.setStatusDoacao(StatusDoacao.ENTREGUE);
        Doacao doacao = doacaoRepository.save(doacaoEncontrada);
        enviarEmailDeAtualizacaoStatusDoacao(doacao, mensagemConfirma);
    }

    public void recusarDoacao(Long idDoacao) {
        Doacao doacaoEncontrada = pesquisarDoacaoPorId(idDoacao);
        doacaoEncontrada.setStatusDoacao(StatusDoacao.RECUSADA);
        Doacao doacao = doacaoRepository.save(doacaoEncontrada);
        enviarEmailDeAtualizacaoStatusDoacao(doacao, mensagemRecusa);
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

    private void enviarEmailDeAtualizacaoStatusDoacao(Doacao doacao, String mensagemEmail){
        Organizacao organizacao = doacao.getCampanha().getOrganizacao();
        EmailModel email = new EmailModel();
        email.setEmailFrom(emailProjeto);
        email.setEmailTo(doacao.getPessoa().getUsuario().getEmail());

        email.setText("Prezado(a) usuário(a),\n \n Trazemos atualizações sobre sua doação para campanha \""
                +doacao.getCampanha().getNome().toLowerCase() +"\". \n Informamos que a organização \""
                +organizacao.getNome().toLowerCase()+"\" "+mensagemEmail);

        email.setSubject("Sua doação foi " + doacao.getStatusDoacao().name().toLowerCase());
        email.setOwnerRef(organizacao.getNome());
        emailService.sendEmail(email);
    }
}
