package com.capgemini.banco.core.controllers;

import com.capgemini.banco.core.dtos.ClienteDto;
import com.capgemini.banco.core.services.ClienteService;
import com.capgemini.banco.settings.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public void registerUser(@RequestBody ClienteDto clienteDto) throws BadRequestException {
        service.register(clienteDto.parseToCliente());
    }
}
