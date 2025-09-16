package com.gabrielmaran.Cadastro.Pessoas.Model;

import com.gabrielmaran.Cadastro.Tasks.Model.TaskModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //Diz que é uma entidade do db
@Table(name = "tb_pessoa") //Informa a tabela especifica do db
@Data
@NoArgsConstructor
public class PessoaModel {

    @Id//Fala que a proxima variavel é o ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Estilo de geração do ID
    private Long id;
    private String nome;
    private String email;
    private int idade;
    @ManyToOne //Uma pessoa pode ter apenas uma task, uma task pode ter +1 pessoa(s)
    @JoinColumn(name = "task_id") //Foreign key
    private TaskModel task;

    public PessoaModel(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    public PessoaModel(String nome, String email, int idade, TaskModel task) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.task = task;
    }
}
