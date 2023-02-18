package com.ownproject.doemais.services.authentication;

import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthenticationService {

    /*

        TODO - Configurar autenticação e autorização JWT nas configurações Spring

          public boolean hasPermission(Principal principal, Long id) {
                // Verificar se o usuário é o proprietário da pessoa ou é um administrador com permissão
                return isOwner(principal, id) || isAdmin(principal);
            }

            private boolean isOwner(Principal principal, Long id) {
                String username = principal.getName();
                Pessoa pessoa = pessoaRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Pessoa não encontrada com ID: " + id));
                return pessoa.getUsuario().getUsername().equals(username);
            }

            private boolean isAdmin(Principal principal) {
                // Implementar lógica para verificar se o usuário é um administrador
                return false;
            }

    * */

    public boolean hasPermission(Principal principal, Long id){
        return true;
    }
}
