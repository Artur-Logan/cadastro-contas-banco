package com.artur.cadastrobanco.controllers;

import com.artur.cadastrobanco.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.artur.cadastrobanco.dto.requests.ContaRequest;
import com.artur.cadastrobanco.dto.responses.ContaResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponse> salvar(@RequestBody ContaRequest contaRequest){
        ContaResponse contaResponse = contaService.salvar(contaRequest);

        return ResponseEntity.ok().body(contaResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponse> obter(@PathVariable Long id){
        ContaResponse contaResponse = contaService.obter(id);

        return ResponseEntity.ok().body(contaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        contaService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ContaResponse>> listarTodos(){
        List<ContaResponse> responses = contaService.listarTodos();

        return ResponseEntity.ok().body(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponse> atualizar(@PathVariable Long id, @RequestBody ContaRequest contaRequest){
        ContaResponse contaResponse = contaService.atualizar(id, contaRequest);

        return ResponseEntity.ok().body(contaResponse);
    }
}
