package com.example.exemplo.repository

import com.example.exemplo.model.Pessoa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PessoaRepository: JpaRepository<Pessoa, Long> {
}