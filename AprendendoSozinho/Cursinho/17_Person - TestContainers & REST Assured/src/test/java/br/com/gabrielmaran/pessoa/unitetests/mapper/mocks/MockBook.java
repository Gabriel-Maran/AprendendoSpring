package br.com.gabrielmaran.pessoa.unitetests.mapper.mocks;

import br.com.gabrielmaran.pessoa.data.dto.BookDTO;
import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.model.Book;
import br.com.gabrielmaran.pessoa.model.Person;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setTitle("Title" + number);
        book.setLaunch_date(Date.from(Instant.now()));
        book.setPrice(BigDecimal.ONE);
        book.setId(number.longValue());
        book.setAuthor("Author" + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setTitle("Title" + number);
        book.setLaunch_date(Date.from(Instant.now()));
        book.setPrice(BigDecimal.ONE);
        book.setId(number.longValue());
        book.setAuthor("Author" + number);
        return book;
    }

}