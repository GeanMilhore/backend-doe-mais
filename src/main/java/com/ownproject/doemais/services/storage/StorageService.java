package com.ownproject.doemais.services.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ownproject.doemais.domain.imagem.Imagem;
import com.ownproject.doemais.repositories.imagem.ImagemRepository;
import com.ownproject.doemais.utils.data.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private ImagemRepository imagemRepository;

    public Imagem uploadFile(MultipartFile file){
        File fileConverted = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        s3client.putObject(new PutObjectRequest(bucketName, fileName, fileConverted));
        String urlImagem = recuperarUrlImagem(fileName);
        fileConverted.delete();
        return imagemRepository.save(criarNovaImagem(file, fileName, urlImagem));
    }

    private static Imagem criarNovaImagem(MultipartFile file, String fileName, String urlImagem) {
        Imagem imagemCreated = new Imagem(file.getOriginalFilename(), fileName, urlImagem);
        imagemCreated.setDataCriacao(DateUtil.dataDeHoje());
        imagemCreated.setDataUltimaEdicao(DateUtil.dataDeHoje());
        return imagemCreated;
    }

    public String deleteFile(Imagem imagem){
        s3client.deleteObject(bucketName, imagem.getNomeCompleto());
        imagemRepository.deleteById(imagem.getId());
        return "file "+imagem.getNomeCompleto()+" deleted";
    }

    private File convertMultipartFileToFile(MultipartFile  file){
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        } catch (IOException e){
            log.error("Error converting multipartfile to file", e);
        }
        return convertedFile;
    }

    private String recuperarUrlImagem(String fileName) {
        return s3client.getObject(bucketName, fileName).getObjectContent().getHttpRequest().getURI().toString();
    }
}