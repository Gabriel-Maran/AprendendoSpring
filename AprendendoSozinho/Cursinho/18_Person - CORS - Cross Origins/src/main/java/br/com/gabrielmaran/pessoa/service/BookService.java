package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.controllers.BookController;
import br.com.gabrielmaran.pessoa.controllers.PersonController;
import br.com.gabrielmaran.pessoa.data.dto.BookDTO;
import br.com.gabrielmaran.pessoa.exception.RequiredObjectIsNullException;
import br.com.gabrielmaran.pessoa.exception.ResourceNotFoundException;
import br.com.gabrielmaran.pessoa.model.Book;
import br.com.gabrielmaran.pessoa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseListObjects;
import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    @Autowired
    BookRepository repository;

    private final Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    public BookDTO findById(Long id) {
        logger.info("Finding one person by id: {}", id);
        var entity =  repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        return addHateoasLinks(parseObject(entity, BookDTO.class));
    }

    public List<BookDTO> findAll() {
        logger.info("Finding all people");
        var books = parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(this::addHateoasLinks);
        return books;
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
       book.add(linkTo(methodOn(BookController.class).findAll()).withRel("findPeople").withType("GET"));
       book.add(linkTo(methodOn(BookController.class).createBook(book)).withRel("create").withType("POST"));
       book.add(linkTo(methodOn(BookController.class).updateBook(book)).withRel("edit").withType("PUT"));
       book.add(linkTo(methodOn(BookController.class).deleteBook(book.getId())).withRel("delete").withType("DELETE"));
        return book;
    }
}
