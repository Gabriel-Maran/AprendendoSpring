package br.com.gabrielmaran.pessoa.repository;

import br.com.gabrielmaran.pessoa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
