package com.artur.cadastrobanco.exceptions.handle;

import lombok.Data;

import java.time.Instant;

@Data
public class StandarError {

    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
}
