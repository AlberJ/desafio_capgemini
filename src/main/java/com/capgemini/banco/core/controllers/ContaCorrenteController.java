package com.capgemini.banco.core.controllers;

import com.capgemini.banco.core.dtos.OperacaoEmContaDto;
import com.capgemini.banco.core.dtos.SaldoDto;
import com.capgemini.banco.core.services.ContaCorrenteService;
import com.capgemini.banco.settings.exceptions.BadRequestException;
import com.capgemini.banco.settings.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping(value = "/clientes")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}/deposito")
    @ResponseStatus(code = HttpStatus.OK)
    public void deposito(@PathVariable Long id, @RequestBody OperacaoEmContaDto operacao)
            throws BadRequestException, NotFoundException {

        contaCorrenteService.depositar(id, operacao.getValor());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}/saque")
    @ResponseStatus(code = HttpStatus.OK)
    public void saque(@PathVariable Long id, @RequestBody OperacaoEmContaDto operacao)
            throws BadRequestException, NotFoundException, ValidationException {

        contaCorrenteService.sacar(id, operacao.getValor());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseBody
    public SaldoDto consultaSaldo(@PathVariable Long id){
        return contaCorrenteService.consultarSaldo(id);
    }
}
