package com.gabrielmaran.Cadastro.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//Basicamente diz q esse mano vai ser um controller com api rest
@RequestMapping//Juntar todas as rotas no mesmo lugar
public class PessoaController {

    @GetMapping("/boasvindas")//Esse é uma rota com a requisição GET (Como tbm tem DELETE, PATCH, PUT e POST)
    public String boasVindas(){
        return "Bem Vindo!";
    }



}
