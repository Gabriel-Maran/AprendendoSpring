package br.com.gabrielmaran.pessoa.repository;

import br.com.gabrielmaran.pessoa.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // É usado para garantir o ACID e ser Atomico quanto as suas decisões.
    // O clearAutomatically = true basicamente diz que não deve segurar em cache este objeto, sempre buscar no banco o mais atual
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
    void disablePerson(@Param("id") Long Id);

    @Query("SELECT p FROM Person p WHERE p.firstName like LOWER(CONCAT('%',:firstName,'%'))")
    Page<Person> findPeopleByName(@Param("firstName") String firstName, Pageable pageable);
}
