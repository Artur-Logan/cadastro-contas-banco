package com.artur.cadastrobanco.service;

import com.artur.cadastrobanco.entities.Conta;
import com.artur.cadastrobanco.exceptions.ContaNotFoundException;
import com.artur.cadastrobanco.repositories.ContaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.artur.cadastrobanco.dto.requests.ContaRequest;
import com.artur.cadastrobanco.dto.responses.ContaResponse;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContaServiceTest {

    @MockBean
    private ContaRepository contaRepository;

    @Autowired
    private ContaService contaService;

    @Test
    void salvarSucesso() {
        //cenario
        ContaRequest contaRequest = getContaRequest();

        Conta conta = new Conta();
        conta.setId(1L);
        conta.setDataCadastro(LocalDate.now());
        conta.setTipoConta(contaRequest.getTipoConta());
        conta.setNome(contaRequest.getNome());
        conta.setChequeEspecial(contaRequest.getChequeEspecial());
        conta.setSobreNome(contaRequest.getSobreNome());
        conta.setSaldo(contaRequest.getSaldo());

        when(contaRepository.save(any()))
                .thenReturn(conta);

        //execução
        ContaResponse contaResponse = contaService.salvar(contaRequest);

        //verificação
        assertNotNull(contaResponse.getId());
        assertEquals(1L, contaResponse.getId());
    }

    @Test
    void atualizarSucesso() {

        //cenario
        Long idConta = 1L;
        ContaRequest contaRequest = getContaRequest();

        Conta conta = new Conta();
        conta.setId(idConta);
        conta.setDataCadastro(LocalDate.now());
        conta.setTipoConta(contaRequest.getTipoConta());
        conta.setNome(contaRequest.getNome());
        conta.setChequeEspecial(contaRequest.getChequeEspecial());
        conta.setSobreNome(contaRequest.getSobreNome());
        conta.setSaldo(contaRequest.getSaldo());

        when(contaRepository.save(any()))
                .thenReturn(conta);

        when(contaRepository.findById(any()))
                .thenReturn(Optional.of(conta));

        //execução
        ContaResponse contaResponse = contaService.atualizar(idConta, contaRequest);

        //verificação
        assertEquals(contaRequest.getNome(), contaResponse.getNome());
        assertEquals(contaRequest.getSaldo(), contaResponse.getSaldo());
        assertEquals(contaRequest.getSobreNome(), contaResponse.getSobreNome());
        assertEquals(contaRequest.getSaldo(), contaResponse.getSaldo());
        assertEquals(contaRequest.getTipoConta(), contaResponse.getTipoConta());
        assertNotNull(contaResponse.getId());
    }

    @Test
    void deletarSucesso() {
        //cenario
        Long idConta = 1L;

        when(contaRepository.findById(any()))
                .thenReturn(Optional.of(getConta(1L)));

        //execução
        contaService.deletar(idConta);
    }

    @Test
    void listarTodosSucesso() {
        //cenario
        Conta conta1 = getConta(1L);
        Conta conta2 = getConta(2L);
        Conta conta3 = getConta(3L);
        Conta conta4 = getConta(4L);
        Conta conta5 = getConta(5L);

        List<Conta> contaList = List.of(
            conta1, conta2, conta3, conta4, conta5
        );

        when(contaRepository.findAll())
                .thenReturn(contaList);

        //execução
        List<ContaResponse> contaResponseList = contaService.listarTodos();

        //verificação
        assertTrue(contaResponseList.size() > 0);
        assertEquals(5, contaResponseList.size());
    }

    @Test
    void obterSucesso() {
        //cenario
        Long id = 1L;

        Conta conta = getConta(id);

        when(contaRepository.findById(any()))
                .thenReturn(Optional.of(conta));

        //execução
        ContaResponse contaResponse = contaService.obter(id);

        //verificação
        assertEquals(id, contaResponse.getId());
    }

    //testes para esperando erro.-----------------------------------------------------------------

    @Test
    void salvarFalha(){
        //cenario
        ContaRequest contaRequest = getContaRequest();

        when(contaRepository.save(any()))
                .thenThrow(new RuntimeException("Simulando erro esperando falha."));

        //execução, espero que dê erro de RunTimeException
        assertThrows(RuntimeException.class,
                () -> contaService.salvar(contaRequest));
    }

    @Test
    void atualizarFalha(){
        //cenario
        Long idConta = 1L;
        ContaRequest contaRequest = getContaRequest();

        Conta conta = null;

        when(contaRepository.save(any()))
                .thenReturn(conta);

        when(contaRepository.findById(any()))
                .thenReturn(Optional.ofNullable(conta));

        //execução
        assertThrows(
                NoSuchElementException.class,
                () -> contaService.atualizar(idConta, contaRequest)
        );
    }

    @Test
    void deletarFalha(){
        //cenario
        when(contaRepository.findById(any()))
                .thenReturn(Optional.ofNullable(null));

        //execução
        assertThrows(ContaNotFoundException.class,
                () -> contaService.deletar(1L));
    }

    @Test
    void listarTodosFalha() {
        //cenario
        when(contaRepository.findAll())
                .thenReturn(null);

        //execução
        assertThrows(
                NullPointerException.class,
                () -> contaService.listarTodos()
        );

    }

    @Test
    void obterFalha() {
        //cenario
        Long id = 1L;

        Conta conta = null;

        when(contaRepository.findById(any()))
                .thenReturn(Optional.ofNullable(conta));

        //execução
        assertThrows(
                ContaNotFoundException.class,
                () -> contaService.obter(id)
        );
    }

    private ContaRequest getContaRequest() {

        ContaRequest contaRequest = new ContaRequest();
        contaRequest.setNome("Teste.");
        contaRequest.setSobreNome("teste");
        contaRequest.setTipoConta("COnta-Corrente");
        contaRequest.setSaldo(1200.00);
        contaRequest.setChequeEspecial(false);

        return contaRequest;
    }

    private Conta getConta(Long id) {

        Conta conta = new Conta();
        conta.setId(id);
        conta.setNome("Conta Teste." + id);
        conta.setSobreNome("Teste");
        conta.setSaldo(1200.00);
        conta.setTipoConta("Teste");
        conta.setChequeEspecial(true);
        conta.setDataCadastro(LocalDate.now());

        return conta;
    }

}