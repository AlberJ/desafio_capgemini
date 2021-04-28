package com.capgemini.banco.core.models;

import com.capgemini.banco.settings.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conta_corrente")
public class ContaCorrente {

    @Id
    @Column(name = "cliente_id")
    private Long id;

    private Double saldo = 0.0;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public ContaCorrente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void deposito(Double valor) {
        this.saldo += valor;
    }

    public Double saque(Double valor) {
        if(this.saldo < valor) throw new BadRequestException("valor inválido");

        this.saldo -= valor;
        return this.saldo;
    }
}
