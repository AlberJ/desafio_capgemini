package com.capgemini.banco.core.services;

import com.capgemini.banco.core.models.Cliente;
import com.capgemini.banco.core.models.ContaCorrente;
import com.capgemini.banco.core.repositories.ContaCorrenteRepository;
import com.capgemini.banco.settings.exceptions.BadRequestException;
import com.capgemini.banco.settings.exceptions.NotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContaCorrenteServiceTest {

    @Mock
    private ContaCorrenteRepository repository;

    @InjectMocks
    private ContaCorrenteService service;

    private ContaCorrente conta;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp(){
        conta = ContaCorrente.builder()
                .id(1L)
                .saldo(0.0)
                .cliente(new Cliente())
                .build();
    }

    @Test
    public void testDeDepositoComSucesso(){
        when(repository.findById(anyLong())).thenReturn(Optional.of(conta));
        Double valorDeposito = 10.0;
        ArgumentCaptor<ContaCorrente> contaArgCaptor = ArgumentCaptor.forClass(ContaCorrente.class);

        service.depositar(anyLong(), valorDeposito);

        verify(repository,times(1)).save(contaArgCaptor.capture());
        assertEquals(valorDeposito, contaArgCaptor.getValue().getSaldo());
    }

    @Test
    public void deveLancarExceptionPorIdNaoEncontrado(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        exceptionRule.expect(NotFoundException.class);
        exceptionRule.expectMessage("cliente não encontrado");

        service.depositar(anyLong(), 0.1);
    }

    @Test
    public void deveLancarExceptionPorValorDeSaqueMaiorDoQueSaldo(){
        conta.setSaldo(10.0);
        when(repository.findById(anyLong())).thenReturn(Optional.of(conta));
        exceptionRule.expect(BadRequestException.class);
        exceptionRule.expectMessage("valor inválido");

        service.sacar(anyLong(), 20.0);
    }
}
