package br.com.gabrielmaran.pessoa.controllers.v1;

import br.com.gabrielmaran.pessoa.data.dto.v1.PersonDTOV1;
import br.com.gabrielmaran.pessoa.service.v1.PersonServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PersonControllerV1 {

    @Autowired //Por pura preguiça de fazer um construtor KKKKKKKKK
    private PersonServiceV1 personService;

    //@RequestMapping(
    //        value = "/{id}",
    //        method = RequestMethod.GET,
    //        produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/v1/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTOV1> findByIdV1(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    // @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTOV1>> findAllV1() {
        return ResponseEntity.ok(personService.findAll());
    }

    //@RequestMapping(
    //        method = RequestMethod.POST,
    //        consumes = MediaType.APPLICATION_JSON_VALUE,
    //        produces = MediaType.APPLICATION_JSON_VALUE
    //)
    @PostMapping(
            value = "/v1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTOV1> createPersonV1(@RequestBody PersonDTOV1 pessoa) {
        return ResponseEntity.ok(personService.createPerson(pessoa));
    }
    //@RequestMapping(
    //        method = RequestMethod.PUT,
    //        consumes = MediaType.APPLICATION_JSON_VALUE,
    //        produces = MediaType.APPLICATION_JSON_VALUE
    //)
    @PutMapping(
            value = "/v1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTOV1> updatePersonV1(@RequestBody PersonDTOV1 person) {
        return ResponseEntity.ok(personService.updatePerson(person));
    }

    //@RequestMapping(
    //        value = "{id}",
    //        method = RequestMethod.DELETE
    //)
    @DeleteMapping(value = "/v1/{id}")
    public ResponseEntity<?> deletePersonV1(@PathVariable("id") Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
