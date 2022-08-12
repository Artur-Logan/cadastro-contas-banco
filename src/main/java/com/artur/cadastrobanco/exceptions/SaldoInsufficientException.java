package com.artur.cadastrobanco.exceptions;

public class SaldoInsufficientException extends RuntimeException{

    public SaldoInsufficientException(String msg) {
        super(msg);
    }
}
