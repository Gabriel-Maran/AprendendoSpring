package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.exception.ResourceNotFoundException;
import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired // Por pura preguiça de fazer um contrutor
    PersonRepository repository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public Person findById(Long id) {
        logger.info("Finding one person by id: " + id);
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
    }

    public List<Person> findAll() {
        logger.info("Finding all people");
        return repository.findAll();
    }

    public Person createPerson(Person pessoa) {
        logger.info("Creating one person");
        return repository.save(pessoa);
    }

    public Person updatePerson(Person pessoa) {
        logger.info("Updating one person");
        Person entity = repository.findById(pessoa.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + pessoa.getId() + " id"));
        entity.setFirstName(pessoa.getFirstName());
        entity.setLastName(pessoa.getLastName());
        entity.setAddress(pessoa.getAddress());
        entity.setGender(pessoa.getGender());

        return repository.save(entity);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting one person");
        repository.deleteById(id);
    }
}
