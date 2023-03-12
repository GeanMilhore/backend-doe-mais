package com.ownproject.doemais.services.perfil;

import com.ownproject.doemais.domain.perfil.Perfil;
import com.ownproject.doemais.repositories.perfil.PerfilRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    PerfilRepository repository;
    public Perfil pesquisarPerfilPorId(Long id){
        return repository.findPerfilById(id).orElseThrow(() -> new EntityNotFoundException("Perfil Ínvalido!"));
    }
}
