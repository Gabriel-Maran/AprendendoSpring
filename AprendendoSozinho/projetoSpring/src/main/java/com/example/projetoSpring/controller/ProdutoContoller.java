package com.example.projetoSpring.controller;


import com.example.projetoSpring.exception.RecursoNaoEncontradoException;
import com.example.projetoSpring.model.Produto;
import com.example.projetoSpring.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoContoller {

    private final ProdutoService produtoService;

    public ProdutoContoller(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping()
    public List<Produto> getAllProdutos(){
        return produtoService.listProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProdutoById(@PathVariable(value = "id") Long id){
            Produto produto = produtoService.buscarPorId(id);
            return ResponseEntity.ok(produto);
    }

    @PostMapping
    public Produto saveProduto(@RequestBody Produto produto){
        return produtoService.salvarProduto(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable(value = "id") Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
