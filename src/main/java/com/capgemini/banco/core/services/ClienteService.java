package com.capgemini.banco.core.services;

import com.capgemini.banco.core.models.Cliente;
import com.capgemini.banco.core.models.ContaCorrente;
import com.capgemini.banco.core.repositories.ClienteRepository;
import com.capgemini.banco.settings.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void registro(Cliente cliente){
        try {
            cliente.setContaCorrente(new ContaCorrente(cliente));
            clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e){
            throw new BadRequestException("CPF já cadastrado");
        } catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
