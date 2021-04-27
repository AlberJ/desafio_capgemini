package com.capgemini.banco.core.services;

import com.capgemini.banco.core.models.Cliente;
import com.capgemini.banco.core.repositories.ClienteRepository;
import com.capgemini.banco.settings.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public void register(Cliente cliente){
        try {
            repository.save(cliente);
        } catch (DataIntegrityViolationException e){
            throw new BadRequestException("CPF já cadastrado");
        } catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
