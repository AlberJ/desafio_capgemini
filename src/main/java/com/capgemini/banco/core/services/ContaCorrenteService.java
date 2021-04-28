package com.capgemini.banco.core.services;

import com.capgemini.banco.core.dtos.SaldoDto;
import com.capgemini.banco.core.models.ContaCorrente;
import com.capgemini.banco.core.repositories.ContaCorrenteRepository;
import com.capgemini.banco.settings.exceptions.BadRequestException;
import com.capgemini.banco.settings.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaCorrenteService {

    @Autowired
    private ContaCorrenteRepository repository;

    public void depositar(Long id, Double valor) throws NotFoundException {
        if(valor < 0) throw new BadRequestException("valor inválido");

        ContaCorrente conta = repository.findById(id).orElseThrow(() -> new NotFoundException("cliente não encontrado"));
        conta.deposito(valor);
        repository.save(conta);
    }

    public void sacar(Long id, Double valor) throws NotFoundException {
        if(valor < 0) throw new BadRequestException("valor inválido");

        ContaCorrente conta = repository.findById(id).orElseThrow(() -> new NotFoundException("cliente não encontrado"));
        conta.saque(valor);
        repository.save(conta).getSaldo();
    }

    public SaldoDto consultarSaldo(Long id) {
        ContaCorrente contaCorrente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("cliente não encontrado"));

        return SaldoDto.parseToDto(contaCorrente);
    }
}
