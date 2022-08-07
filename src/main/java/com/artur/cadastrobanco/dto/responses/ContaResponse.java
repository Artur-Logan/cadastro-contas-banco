package com.artur.cadastrobanco.dto.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContaResponse {

    private Long id;
    private String nome;
    private String sobreNome;
    private String tipoConta;
    private Double saldo;
    private LocalDate dataCadastro;
    private Boolean chequeEspecial;
}
