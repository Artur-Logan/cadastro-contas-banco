package com.artur.cadastrobanco.exceptions.handle;

import com.artur.cadastrobanco.exceptions.ContaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandarError> handle(Exception exception, HttpServletRequest request){
        StandarError standarError = new StandarError();

        standarError.setTimestamp(Instant.now());
        standarError.setStatus(HttpStatus.NOT_FOUND.value());
        standarError.setMessage(exception.getMessage());
        standarError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standarError);
    }

    @ExceptionHandler(ContaNotFoundException.class)
    public ResponseEntity<StandarError> handle(ContaNotFoundException exception, HttpServletRequest request){
        StandarError standarError = new StandarError();

        standarError.setTimestamp(Instant.now());
        standarError.setStatus(HttpStatus.NOT_FOUND.value());
        standarError.setMessage(exception.getMessage());
        standarError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standarError);
    }
}
