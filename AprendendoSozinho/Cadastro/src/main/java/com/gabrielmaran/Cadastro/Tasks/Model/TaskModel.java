package com.gabrielmaran.Cadastro.Tasks.Model;

import com.gabrielmaran.Cadastro.Pessoas.Model.PessoaModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_task")
@Data //Cria todos os getters e setters
@NoArgsConstructor // Cria automatico um contrutor sem argumento algum, ou seja, vazio
//@AllArgsConstructor // Cria autoamtico um construtor com todos os argumentos, ou seja, completo
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nome;
    private boolean isCompleted;
    @OneToMany(mappedBy = "task")//Uma task pode ter 1+ pessoas
    private List<PessoaModel> pessoas;

    public TaskModel(String nome, boolean isCompleted) {
        this.nome = nome;
        this.isCompleted = isCompleted;
    }

    public TaskModel(String nome, boolean isCompleted, List<PessoaModel> pessoas) {
        this.nome = nome;
        this.isCompleted = isCompleted;
        this.pessoas = pessoas;
    }
}
