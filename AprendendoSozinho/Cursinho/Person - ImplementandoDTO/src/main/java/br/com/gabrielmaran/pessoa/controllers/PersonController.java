package br.com.gabrielmaran.pessoa.controllers;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PersonController {

    @Autowired //Por pura preguiça de fazer um construtor KKKKKKKKK
    private PersonService personService;

    //@RequestMapping(
    //        value = "/{id}",
    //        method = RequestMethod.GET,
    //        produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    // @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    //@RequestMapping(
    //        method = RequestMethod.POST,
    //        consumes = MediaType.APPLICATION_JSON_VALUE,
    //        produces = MediaType.APPLICATION_JSON_VALUE
    //)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO pessoa) {
        return ResponseEntity.ok(personService.createPerson(pessoa));
    }

    //@RequestMapping(
    //        method = RequestMethod.PUT,
    //        consumes = MediaType.APPLICATION_JSON_VALUE,
    //        produces = MediaType.APPLICATION_JSON_VALUE
    //)
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO person) {
        return ResponseEntity.ok(personService.updatePerson(person));
    }

    //@RequestMapping(
    //        value = "{id}",
    //        method = RequestMethod.DELETE
    //)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
