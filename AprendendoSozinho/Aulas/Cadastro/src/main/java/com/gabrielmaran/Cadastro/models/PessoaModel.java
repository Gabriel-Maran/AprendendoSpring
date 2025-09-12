package com.gabrielmaran.Cadastro.models;

import jakarta.persistence.*;

@Entity //Diz que é uma entidade do db
@Table(name = "tb_cadastro") //Informa a tabela especifica do db
public class PessoaModel {
    @Id//Fala que a proxima variavel é o ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Estilo de geração do ID
    private Long id;
    private String nome;
    private String email;
    private int idade;

    public PessoaModel() {

    }

    public PessoaModel(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
