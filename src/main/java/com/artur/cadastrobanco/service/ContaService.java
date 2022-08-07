package com.artur.cadastrobanco.service;

import com.artur.cadastrobanco.entities.Conta;
import com.artur.cadastrobanco.exceptions.SaldoInsuficienteException;
import com.artur.cadastrobanco.mappers.MapperConta;
import com.artur.cadastrobanco.repositories.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.artur.cadastrobanco.dto.requests.ContaRequest;
import com.artur.cadastrobanco.dto.responses.ContaResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final MapperConta mapperConta;

    public ContaResponse salvar(ContaRequest contaRequest){

        Conta conta = mapperConta.toModel(contaRequest);
        conta.setDataCadastro(LocalDate.now());

        while (conta.getChequeEspecial() == false) {
            if (conta.getSaldo() < 0.0){
                throw new SaldoInsuficienteException("Saldo Insuficiente!");
            }
        }
        contaRepository.save(conta);

        return mapperConta.toResponse(conta);
    }

    public ContaResponse obter(Long id){
        Conta conta = contaRepository.findById(id).get();

        return mapperConta.toResponse(conta);
    }

    public void deletar(Long id){
        Conta conta = contaRepository.findById(id).get();

        contaRepository.delete(conta);

    }

    public List<ContaResponse> listarTodos(){
        List<Conta> contaList = contaRepository.findAll();

        //decorar linha a baixo
        return contaList.stream().map(mapperConta::toResponse).collect(Collectors.toList());
    }

    public ContaResponse atualizar(Long id, ContaRequest contaRequest){
        Conta conta = contaRepository.findById(id).get();

        mapperConta.atualizar(contaRequest,conta);

        contaRepository.save(conta);

        return mapperConta.toResponse(conta);
    }
}