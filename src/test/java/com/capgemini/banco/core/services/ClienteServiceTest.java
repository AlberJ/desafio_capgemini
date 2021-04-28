package com.capgemini.banco.core.services;

import com.capgemini.banco.core.models.Cliente;
import com.capgemini.banco.core.repositories.ClienteRepository;
import com.capgemini.banco.settings.exceptions.BadRequestException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp(){
        cliente = Cliente.builder()
                .clienteId(1L)
                .cpf("47501982856")
                .nome("Nome de Teste")
                .build();
    }

    @Test
    public void testDeSucesso(){
        ArgumentCaptor<Cliente> argumentCaptorCliente = ArgumentCaptor.forClass(Cliente.class);

        service.registro(cliente);

        verify(clienteRepository, times(1)).save(argumentCaptorCliente.capture());
        assertNotNull(argumentCaptorCliente.getValue());
        assertNotNull(argumentCaptorCliente.getValue().getContaCorrente());
        assertEquals((Double)0.0, argumentCaptorCliente.getValue().getContaCorrente().getSaldo());
    }

    @Test
    public void deveLancarExceptionPorCpfJaCadastrado(){
        cliente.setCpf("cpfInvalido");
        exceptionRule.expect(BadRequestException.class);
        exceptionRule.expectMessage("CPF já cadastrado");

        when(clienteRepository.save(cliente)).thenThrow(DataIntegrityViolationException.class);

        service.registro(cliente);
    }

}
