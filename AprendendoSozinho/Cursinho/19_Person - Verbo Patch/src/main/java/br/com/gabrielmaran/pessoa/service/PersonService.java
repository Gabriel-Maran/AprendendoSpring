package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.controllers.PersonController;
import br.com.gabrielmaran.pessoa.exception.RequiredObjectIsNullException;
import br.com.gabrielmaran.pessoa.exception.ResourceNotFoundException;
import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseListObjects ;
import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseObject ;
import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired // Por pura preguiça de fazer um contrutor
    PersonRepository repository;

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public PersonDTO findById(Long id) {
        logger.info("Finding one person by id: {}", id);
        var entity =  repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        return addHateoasLinks(parseObject(entity, PersonDTO.class));
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all people");
        var people = parseListObjects(repository.findAll(), PersonDTO.class);
        people.forEach(this::addHateoasLinks);
        return people;

    }

    public PersonDTO createPerson(PersonDTO pessoa) {
        logger.info("Creating one person");
        if(pessoa == null) throw new RequiredObjectIsNullException();
        var entity = parseObject(pessoa, Person.class);
        return addHateoasLinks(parseObject(repository.save(entity), PersonDTO.class));
    }

    public PersonDTO updatePerson(PersonDTO pessoa) {
        logger.info("Updating one person");
        if(pessoa == null) throw new RequiredObjectIsNullException();
        Person entity = repository.findById(pessoa.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + pessoa.getId() + " id"));
        entity.setFirstName(pessoa.getFirstName());
        entity.setLastName(pessoa.getLastName());
        entity.setAddress(pessoa.getAddress());
        entity.setGender(pessoa.getGender());

        return addHateoasLinks(parseObject(repository.save(entity), PersonDTO.class));
    }

    @Transactional // Basicamente torna isto atomico, uma execução por vez e alta confiabilidade
    public PersonDTO disablePerson(Long id) {
        logger.info("Disabling one person");
        repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        repository.disablePerson(id);
        return addHateoasLinks(parseObject(repository.findById(id).get(), PersonDTO.class));
    }

    public void deletePerson(Long id) {
        logger.info("Deleting one person");
        repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        repository.deleteById(id);
    }

    private PersonDTO addHateoasLinks(PersonDTO person){
        person.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel().withType("GET"));
        person.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findPeople").withType("GET"));
        person.add(linkTo(methodOn(PersonController.class).createPerson(person)).withRel("create").withType("POST"));
        person.add(linkTo(methodOn(PersonController.class).updatePerson(person)).withRel("edit").withType("PUT"));
        person.add(linkTo(methodOn(PersonController.class).disablePerson(person.getId())).withRel("disable").withType("PATCH"));
        person.add(linkTo(methodOn(PersonController.class).deletePerson(person.getId())).withRel("delete").withType("DELETE"));
        return person;
    }
}
