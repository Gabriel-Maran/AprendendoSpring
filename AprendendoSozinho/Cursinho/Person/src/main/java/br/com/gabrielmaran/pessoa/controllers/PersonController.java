package br.com.gabrielmaran.pessoa.controllers;

import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PersonController {

    @Autowired //Por pura preguiça de fazer um construtor KKKKKKKKK
    private PersonService personService;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> findAll(){
        return ResponseEntity.ok(personService.findAll());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Person> createPerson(@RequestBody Person pessoa){
        return ResponseEntity.ok(personService.createPerson(pessoa));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Person> updatePerson(@RequestBody Person person){
        return ResponseEntity.ok(personService.updatePerson(person));
    }

    @RequestMapping(
            value = "{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id){
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
