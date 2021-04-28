package com.capgemini.banco.core.dtos;

import com.capgemini.banco.core.models.ContaCorrente;

public class SaldoDto {
    public Double saldo;

    public SaldoDto(Double saldo) {
        this.saldo = saldo;
    }

    public static SaldoDto parseToDto(ContaCorrente conta){
        return new SaldoDto(conta.getSaldo());
    }
}
