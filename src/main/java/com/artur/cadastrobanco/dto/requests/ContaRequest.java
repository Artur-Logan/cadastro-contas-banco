package com.artur.cadastrobanco.dto.requests;

import lombok.Data;

@Data
public class ContaRequest {

    private String nome;
    private String sobreNome;
    private String tipoConta;
    private Double saldo;
    private Boolean chequeEspecial;
}
