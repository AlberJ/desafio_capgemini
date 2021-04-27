package com.capgemini.banco.core.dtos;

import com.capgemini.banco.core.models.Cliente;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Size;

@Data
@Builder
public class ClienteDto {

    private String nome;

    @CPF(message = "CPF inválido")
    @Size(min = 11, max = 11, message = "CPF inválido")
    private String cpf;

    public Cliente parseToCliente(){
        return Cliente.builder()
                .cpf(this.cpf)
                .nome(this.nome)
                .build();
    }

}
