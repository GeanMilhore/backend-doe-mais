package com.ownproject.doemais.services.frete;

import com.ownproject.doemais.domain.endereco.Endereco;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FreteService {

    public BigDecimal calcularFrete(Endereco origem, Endereco destino){
        if(origem.getBairro() == destino.getBairro())
            throw new IllegalArgumentException("Não é possível fazer envios para o mesmo bairro.");

        if(origem.getLocalidade() == destino.getLocalidade()) return new BigDecimal(20);
        if(origem.getUf() == destino.getUf()) return new BigDecimal(50);
        return new BigDecimal(100);
    }
}
