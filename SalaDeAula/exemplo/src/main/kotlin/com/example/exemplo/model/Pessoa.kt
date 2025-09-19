package com.example.exemplo.model

import jakarta.persistence.*

@Entity
@Table(name = "pessoa")
data class Pessoa(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null, //Null para informar a JPA que novos objetos serão novas linhas no banco
    var nome: String,
    var idade: Int,
    val cpf: String,
) {
}