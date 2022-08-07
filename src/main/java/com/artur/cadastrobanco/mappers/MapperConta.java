package com.artur.cadastrobanco.mappers;

import com.artur.cadastrobanco.entities.Conta;
import org.mapstruct.Mapper;

import com.artur.cadastrobanco.dto.requests.ContaRequest;
import com.artur.cadastrobanco.dto.responses.ContaResponse;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MapperConta {

    Conta toModel(ContaRequest contaRequest);
    ContaResponse toResponse(Conta conta);
    Conta atualizar(ContaRequest contaRequest, @MappingTarget Conta conta);
}
