package br.com.gabrielmaran.pessoa.repository;

import br.com.gabrielmaran.pessoa.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
