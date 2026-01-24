package br.com.gabrielmaran.pessoa.controllers;

import br.com.gabrielmaran.pessoa.controllers.docs.PersonControllerDocs;
import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoa/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired //Por pura preguiça de fazer um construtor KKKKKKKKK
    private PersonService personService;

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public ResponseEntity<PersonDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO pessoa) {
        return ResponseEntity.ok(personService.createPerson(pessoa));
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO person) {
        return ResponseEntity.ok(personService.updatePerson(person));
    }

    @DeleteMapping(value = "{id}")
    @Override
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}