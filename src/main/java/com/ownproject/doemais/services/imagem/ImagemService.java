package com.ownproject.doemais.services.imagem;


import com.ownproject.doemais.controllers.imagem.dto.response.ImagemDto;
import com.ownproject.doemais.domain.campanha.Campanha;
import com.ownproject.doemais.domain.doacao.doacaoItem.DoacaoItem;
import com.ownproject.doemais.domain.imagem.Imagem;
import com.ownproject.doemais.domain.usuario.Usuario;
import com.ownproject.doemais.interfaces.imagem.ImagemIlustravel;
import com.ownproject.doemais.repositories.campanha.CampanhaRepository;
import com.ownproject.doemais.repositories.doacao.DoacaoItemRepository;
import com.ownproject.doemais.repositories.usuario.UsuarioRepository;
import com.ownproject.doemais.services.authentication.TokenService;
import com.ownproject.doemais.services.campanha.CampanhaService;
import com.ownproject.doemais.services.doacao.DoacaoService;
import com.ownproject.doemais.services.storage.StorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagemService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CampanhaService campanhaService;

    @Autowired
    private DoacaoService doacaoService;

    @Autowired
    private CampanhaRepository campanhaRepository;
    @Autowired
    private DoacaoItemRepository doacaoItemRepository;

    @Transactional
    public ImagemDto vincularImagemUsuario(MultipartFile file){
        Usuario usuarioLogado = tokenService.getUsuarioLogado();
        if(usuarioLogado.getImagem() != null) storageService.deleteFile(usuarioLogado.getImagem());
        Imagem imagem = storageService.uploadFile(file);
        usuarioLogado.setImagem(imagem);
        usuarioRepository.save(usuarioLogado);
        return new ImagemDto(imagem);
    }

    @Transactional
    public ImagemDto vincularImagemCampanha(Long idCampanha, MultipartFile file){
        Campanha campanha = campanhaService.pesquisarCampanha(idCampanha);
        if(campanha.getImagem() != null) storageService.deleteFile(campanha.getImagem());
        Imagem imagem = storageService.uploadFile(file);
        campanha.setImagem(imagem);
        campanhaRepository.save(campanha);
        return new ImagemDto(imagem);
    }

    @Transactional
    public ImagemDto vincularImagemDoacao(Long idDoacao, MultipartFile file){
        DoacaoItem item = doacaoService.pesquisarItemDoacaoPorId(idDoacao);
        if(item.getImagem() != null) storageService.deleteFile(item.getImagem());
        Imagem imagem = storageService.uploadFile(file);
        item.setImagem(imagem);
        doacaoItemRepository.save(item);
        return new ImagemDto(imagem);
    }

    @Transactional
    public void removerImagemUsuario(){
        Usuario usuarioLogado = tokenService.getUsuarioLogado();
        removerImagemEntidade(usuarioLogado);
        usuarioRepository.save(usuarioLogado);
    }

    @Transactional
    public void removerImagemCampanha(Long idCampanha){
        Campanha campanha = campanhaService.pesquisarCampanha(idCampanha);
        removerImagemEntidade(campanha);
        campanhaRepository.save(campanha);
    }

    @Transactional
    public void removerImagemDoacao(Long idDoacao){
        DoacaoItem item = doacaoService.pesquisarItemDoacaoPorId(idDoacao);
        removerImagemEntidade(item);
        doacaoItemRepository.save(item);
    }

    public void removerImagemEntidade(ImagemIlustravel entidade){
        if(entidade.getImagem() == null) throw new IllegalArgumentException("A entidade já se encontra sem imagem");
        storageService.deleteFile(entidade.getImagem());
        entidade.setImagem(null);
    }
}
