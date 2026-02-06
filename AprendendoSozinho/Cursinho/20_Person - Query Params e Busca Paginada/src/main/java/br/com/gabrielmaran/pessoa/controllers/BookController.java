package br.com.gabrielmaran.pessoa.controllers;

import br.com.gabrielmaran.pessoa.controllers.docs.BookControllerDocs;
import br.com.gabrielmaran.pessoa.data.dto.BookDTO;
import br.com.gabrielmaran.pessoa.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//CORS de acesso a API, via CrossOrigin nivel controller:
// - @CrossOrigin //Habilita a todos mandaresm requisições
// - @CrossOrigin(origins = "http://localhost:8080") //Habilita apenas as origens dentro de 'origins'

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Books", description = "Endpoints for Managing Books")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookService bookService;

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public ResponseEntity<BookDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(
                @RequestParam(value = "page", defaultValue = "0") Integer page,
                @RequestParam(value = "size", defaultValue = "12") Integer size,
                @RequestParam(value = "direction", defaultValue = "asc") String direction
            ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(bookService.findAll(PageRequest.of(page, size, Sort.by(sortDirection, "title"))));
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO pessoa) {
        return ResponseEntity.ok(bookService.createBook(pessoa));
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO person) {
        return ResponseEntity.ok(bookService.updateBook(person));
    }

    @DeleteMapping(value = "{id}")
    @Override
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}