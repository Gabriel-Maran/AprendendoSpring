package br.com.gabrielmaran.pessoa.controllers.v2;

import br.com.gabrielmaran.pessoa.data.dto.v2.PersonDTOV2;
import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.service.v2.PersonServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PersonControllerV2 {

    @Autowired //Por pura preguiça de fazer um construtor KKKKKKKKK
    private PersonServiceV2 personService;

    //@RequestMapping(
    //        value = "/{id}",
    //        method = RequestMethod.GET,
    //        produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/v2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTOV2> findByIdV2(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personService.findByIdV2(id));
    }

    // @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/v2",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTOV2>> findAllV2() {
        return ResponseEntity.ok(personService.findAllV2());
    }

    //@RequestMapping(
    //        method = RequestMethod.POST,
    //        consumes = MediaType.APPLICATION_JSON_VALUE,
    //        produces = MediaType.APPLICATION_JSON_VALUE
    //)
    @PostMapping(
            value = "/v2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTOV2> createPersonV2(@RequestBody Person pessoa) {
        PersonDTOV2 pessoaCreated = personService.createPersonV2(pessoa);
        return ResponseEntity.ok().body(pessoaCreated);
    }

    //@RequestMapping(
    //        method = RequestMethod.PUT,
    //        consumes = MediaType.APPLICATION_JSON_VALUE,
    //        produces = MediaType.APPLICATION_JSON_VALUE
    //)
    @PutMapping(
            value = "/v2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTOV2> updatePersonV2(@RequestBody PersonDTOV2 person) {
        return ResponseEntity.ok(personService.updatePersonV2(person));
    }

    //@RequestMapping(
    //        value = "{id}",
    //        method = RequestMethod.DELETE
    //)
    @DeleteMapping(value = "/v2/{id}")
    public ResponseEntity<?> deletePersonV2(@PathVariable("id") Long id) {
        personService.deletePersonV2(id);
        return ResponseEntity.noContent().build();
    }
}
