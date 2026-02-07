package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.controllers.BookController;
import br.com.gabrielmaran.pessoa.data.dto.BookDTO;
import br.com.gabrielmaran.pessoa.exception.RequiredObjectIsNullException;
import br.com.gabrielmaran.pessoa.exception.ResourceNotFoundException;
import br.com.gabrielmaran.pessoa.model.Book;
import br.com.gabrielmaran.pessoa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;


import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookDTO> assembler;

    private final Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    public BookDTO findById(Long id) {
        logger.info("Finding one person by id: {}", id);
        var entity =  repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        return addHateoasLinks(parseObject(entity, BookDTO.class));
    }

    public PagedModel<EntityModel<BookDTO>> findAll(Pageable pageable) {
        logger.info("Finding all people");
        var books = repository.findAll(
                        PageRequest.of(
                                pageable.getPageNumber(),
                                Math.min(100, pageable.getPageSize()),
                                pageable.getSort()
                        )
        );
        var booksWithHateos = books.map(b -> addHateoasLinks(parseObject(b, BookDTO.class)));
        Link findAllLink = WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder
                                .methodOn(BookController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        Math.min(100, pageable.getPageSize()),
                                        "desc".contains(pageable.getSort().toString()) ? "asc" : "desc"
                                )
                ).withSelfRel();
        return assembler.toModel(booksWithHateos, findAllLink);
    }

    public BookDTO createBook(BookDTO book) {
        logger.info("Creating one person");
        if(book == null) throw new RequiredObjectIsNullException();
        var entity = parseObject(book, Book.class);
        return addHateoasLinks(parseObject(repository.save(entity), BookDTO.class));
    }

    public BookDTO updateBook(BookDTO book) {
        logger.info("Updating one person");
        if(book == null) throw new RequiredObjectIsNullException();
        Book entity = repository.findById(book.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + book.getId() + " id"));
        entity.setAuthor(book.getAuthor());
        entity.setLaunch_date(book.getLaunch_date());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        return addHateoasLinks(parseObject(repository.save(entity), BookDTO.class));
    }

    public void deleteBook(Long id) {
        logger.info("Deleting one book");
        repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        repository.deleteById(id);
    }

    private BookDTO addHateoasLinks(BookDTO book){
       book.add(linkTo(methodOn(BookController.class).findById(book.getId())).withSelfRel().withType("GET"));
       book.add(linkTo(methodOn(BookController.class).findAll(0, 12, "asc")).withRel("findPeople").withType("GET"));
       book.add(linkTo(methodOn(BookController.class).createBook(book)).withRel("create").withType("POST"));
       book.add(linkTo(methodOn(BookController.class).updateBook(book)).withRel("edit").withType("PUT"));
       book.add(linkTo(methodOn(BookController.class).deleteBook(book.getId())).withRel("delete").withType("DELETE"));
        return book;
    }
}
