package com.example.exemplo.contoller

import com.example.exemplo.model.Pessoa
import com.example.exemplo.repository.PessoaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@Controller //Controller permite ter VIEW, para aplicações web
@RestController //RestController não pode usar VIEW, mais rapida(para APIs)
@RequestMapping("/pessoa")
class PessoaController (
    private val pessoaRepository: PessoaRepository
){
    @PostMapping("/register")
    fun cadastrarPessoa(@RequestBody pessoa:Pessoa)
    : ResponseEntity<Pessoa>{
        return ResponseEntity.ok(pessoaRepository.save<Pessoa>(pessoa))
    }

}