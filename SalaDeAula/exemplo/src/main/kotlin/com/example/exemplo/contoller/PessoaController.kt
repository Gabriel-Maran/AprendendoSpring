package com.example.exemplo.contoller

import com.example.exemplo.model.Pessoa
import com.example.exemplo.repository.PessoaRepository
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

//@Controller //Controller permite ter VIEW, para aplicações web
@RestController //RestController não pode usar VIEW, mais rapida(para APIs)
@RequestMapping("/pessoa")
class PessoaController(
    private val pessoaRepository: PessoaRepository
) {
    @PostMapping
    fun cadastrarPessoa(@RequestBody pessoa: Pessoa)
            : ResponseEntity<Pessoa> {
        return ResponseEntity.ok(pessoaRepository.save<Pessoa>(pessoa))
    }

    @GetMapping
    fun getAllPessoa(): List<Pessoa> {
        return pessoaRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): ResponseEntity<Pessoa>{
        var pessoa = pessoaRepository.findById(id)
        if(pessoa.isEmpty) return ResponseEntity.notFound().build   ()
        return ResponseEntity.of(pessoa)
    }

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable("id") id: Long): ResponseEntity<Void>{
        if (pessoaRepository.findById(id).isPresent){
            pessoaRepository.deleteById(id)
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    fun atualizarPessoa(@RequestBody pessoa:Pessoa, @PathVariable("id") id: Long): ResponseEntity<Pessoa> {
        val pessoaAtualizando = pessoaRepository.findById(id)
        if(pessoaAtualizando.isEmpty) return ResponseEntity.notFound().build<Pessoa>()
        if (pessoa.nome != "") pessoaAtualizando.get().nome = pessoa.nome
        if (pessoa.idade != 0) pessoaAtualizando.get().idade = pessoa.idade
        if (pessoa.cpf != "") pessoaAtualizando.get().cpf = pessoa.cpf
        return ResponseEntity.ok(pessoaRepository.save<Pessoa>(pessoaAtualizando.get()))
    }

    @PutMapping("{id}")
    fun atualizarPessoaCompleta(@RequestBody pessoa:Pessoa, @PathVariable("id") id: Long): ResponseEntity<Pessoa>{
        val pessoaAtualizando = pessoaRepository.findById(id)
        if(pessoaAtualizando.isEmpty) return ResponseEntity.notFound().build<Pessoa>()
        pessoaAtualizando.get().nome = pessoa.nome
        pessoaAtualizando.get().idade = pessoa.idade
        pessoaAtualizando.get().cpf = pessoa.cpf
        return ResponseEntity.ok(pessoaRepository.save<Pessoa>(pessoaAtualizando.get()))
    }
sdsdsds}
