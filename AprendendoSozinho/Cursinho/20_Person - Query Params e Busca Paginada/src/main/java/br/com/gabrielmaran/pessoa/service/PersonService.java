package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.controllers.PersonController;
import br.com.gabrielmaran.pessoa.exception.RequiredObjectIsNullException;
import br.com.gabrielmaran.pessoa.exception.ResourceNotFoundException;
import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseObject ;
import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());


    public PersonDTO findById(Long id) {
        logger.info("Finding one person by id: {}", id);
        var entity =  repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        return addHateoasLinks(parseObject(entity, PersonDTO.class));
    }

    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
        logger.info("Finding all people");
        Pageable pageableVerified =
                PageRequest.of(
                        pageable.getPageNumber(),
                        Math.min(100, pageable.getPageSize()),
                        pageable.getSort()
                );
        var people = repository.findAll(pageableVerified);
        var peopleDTOWithHateoas = people.map(p -> addHateoasLinks(parseObject(p, PersonDTO.class)));

        Link findAllLink = WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder
                                .methodOn(PersonController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        Math.min(100, pageable.getPageSize()),
                                        "desc".contains(pageable.getSort().toString()) ? "asc" : "desc"
                                )
                ).withSelfRel();

        return assembler.toModel(peopleDTOWithHateoas, findAllLink);
    }

    public PagedModel<EntityModel<PersonDTO>> findAllByName(String firstName, Pageable pageable) {
        logger.info("Finding all people by name");
        Pageable pageableVerified =
                PageRequest.of(
                        pageable.getPageNumber(),
                        Math.min(100, pageable.getPageSize()),
                        pageable.getSort()
                );
        var people = repository.findPeopleByName(
                firstName,
                pageableVerified
        );
        var peopleDTOWithHateoas = people.map(p -> addHateoasLinks(parseObject(p, PersonDTO.class)));

        Link findAllLink = WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder
                                .methodOn(PersonController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        Math.min(100, pageable.getPageSize()),
                                        "desc".contains(pageable.getSort().toString()) ? "asc" : "desc"
                                )
                ).withSelfRel();

        return assembler.toModel(peopleDTOWithHateoas, findAllLink);
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
        person.add(linkTo(methodOn(PersonController.class).findAll(0, 12, "asc")).withRel("findPeople").withType("GET"));
        person.add(linkTo(methodOn(PersonController.class).findPeopleByName("Gabriel",0, 12, "asc")).withRel("findPeopleByName").withType("GET"));
        person.add(linkTo(methodOn(PersonController.class).createPerson(person)).withRel("create").withType("POST"));
        person.add(linkTo(methodOn(PersonController.class).updatePerson(person)).withRel("edit").withType("PUT"));
        person.add(linkTo(methodOn(PersonController.class).disablePerson(person.getId())).withRel("disable").withType("PATCH"));
        person.add(linkTo(methodOn(PersonController.class).deletePerson(person.getId())).withRel("delete").withType("DELETE"));
        return person;
    }
}
