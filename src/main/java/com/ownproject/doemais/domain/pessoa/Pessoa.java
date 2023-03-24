package com.ownproject.doemais.domain.pessoa;


import com.ownproject.doemais.domain.conta.BaseConta;
import com.ownproject.doemais.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa extends BaseConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nome;

    private String cpf;

    private LocalDateTime dataNascimento;
}
