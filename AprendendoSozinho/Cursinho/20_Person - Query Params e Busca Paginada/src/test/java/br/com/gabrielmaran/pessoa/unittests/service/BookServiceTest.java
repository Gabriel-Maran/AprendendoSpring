package br.com.gabrielmaran.pessoa.unittests.service;

import br.com.gabrielmaran.pessoa.data.dto.BookDTO;
import br.com.gabrielmaran.pessoa.exception.RequiredObjectIsNullException;
import br.com.gabrielmaran.pessoa.model.Book;
import br.com.gabrielmaran.pessoa.repository.BookRepository;
import br.com.gabrielmaran.pessoa.service.BookService;
import br.com.gabrielmaran.pessoa.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Links;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Tudo que for testado desta vez só existirá esta vez
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @Mock
    BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        var result = service.findById(1L);
        assertNotNull(result, "Resultado null");
        assertNotNull(result.getId(), "Id null");
        assertNotNull(result.getLinks(), "Sem links/links nulls");
        var links = result.getLinks();

        //Teste de links
        assertLink(links, "self", "/api/book/v1/" + book.getId(), "GET");
        assertLink(links, "findPeople", "/api/book/v1?page=0&size=12&direction=asc", "GET");
        assertLink(links, "create", "/api/book/v1", "POST");
        assertLink(links, "edit", "/api/book/v1", "PUT");
        assertLink(links, "delete", "/api/book/v1/" + book.getId(), "DELETE");

        //Teste de variaveis do Objeto
        assertEquals("Author" + result.getId(), result.getAuthor(), "Author não é igual ao Mock");
        assertEquals("Title" + result.getId(), result.getTitle(), "Title não é igual ao Mock");
        assertEquals(BigDecimal.ONE.toString(), result.getPrice().toString(), "Price não é igual ao Mock");
    }

    @Test
    void findAll() {
        List<Book> people = input.mockEntityList();
        for (Book book : people) {
            when(repository.findById(book.getId())).thenReturn(Optional.of(book));
            var result = service.findById(book.getId());
            assertNotNull(result, "Resultado null");
            assertNotNull(result.getId(), "Id null");
            assertNotNull(result.getLinks(), "Sem links/links nulls");
            var links = result.getLinks();

            //Teste de links
            assertLink(links, "self", "/api/book/v1/" + book.getId(), "GET");
            assertLink(links, "findPeople", "/api/book/v1?page=0&size=12&direction=asc", "GET");
            assertLink(links, "create", "/api/book/v1", "POST");
            assertLink(links, "edit", "/api/book/v1", "PUT");
            assertLink(links, "delete", "/api/book/v1/" + book.getId(), "DELETE");

            //Teste de variaveis do Objeto
            assertEquals("Author" + result.getId(), result.getAuthor(), "Author não é igual ao Mock");
            assertEquals("Title" + result.getId(), result.getTitle(), "Title não é igual ao Mock");
            assertEquals(BigDecimal.ONE.toString(), result.getPrice().toString(), "Price não é igual ao Mock");
        }
    }

    @Test
    void createPerson() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        BookDTO dto = input.mockDTO(1);

        when(repository.save(any(Book.class))).thenReturn(persisted);
        var result = service.createBook(dto);
        assertNotNull(result, "Resultado null");
        assertNotNull(result.getId(), "Id null");
        assertNotNull(result.getLinks(), "Sem links/links nulls");
        var links = result.getLinks();

        //Teste de links
        assertLink(links, "self", "/api/book/v1/" + book.getId(), "GET");
        assertLink(links, "findPeople", "/api/book/v1?page=0&size=12&direction=asc", "GET");
        assertLink(links, "create", "/api/book/v1", "POST");
        assertLink(links, "edit", "/api/book/v1", "PUT");
        assertLink(links, "delete", "/api/book/v1/" + book.getId(), "DELETE");

        //Teste de variaveis do Objeto
        assertEquals("Author" + result.getId(), result.getAuthor(), "Author não é igual ao Mock");
        assertEquals("Title" + result.getId(), result.getTitle(), "Title não é igual ao Mock");
        assertEquals(BigDecimal.ONE.toString(), result.getPrice().toString(), "Price não é igual ao Mock");
        assertEquals(Date.from(Instant.now()).toString(), result.getLaunch_date().toString(), "Launch_date não é igual ao Mock");
    }

    @Test
    void testCreateWithNullPerson() {
        Exception ex = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.createBook(null);
                }
        );

        String expectedMessage = "It's not allowed to persist a null Object";
        assertEquals(ex.getMessage(), expectedMessage);
    }

    @Test
    void updatePerson() {
        Book book = input.mockEntity(1);
        Book persisted = book;
        BookDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(persisted);

        var result = service.updateBook(dto);
        assertNotNull(result, "Resultado null");
        assertNotNull(result.getId(), "Id null");
        assertNotNull(result.getLinks(), "Sem links/links nulls");
        var links = result.getLinks();

        links.forEach(l -> {
                    System.out.println(l.toString());
                }
        );
        System.out.println(book.getTitle());

        //Teste de links
        assertLink(links, "self", "/api/book/v1/" + book.getId(), "GET");
        assertLink(links, "findPeople", "/api/book/v1?page=0&size=12&direction=asc", "GET");
        assertLink(links, "create", "/api/book/v1", "POST");
        assertLink(links, "edit", "/api/book/v1", "PUT");
        assertLink(links, "delete", "/api/book/v1/" + book.getId(), "DELETE");

        //Teste de variaveis do Objeto
        assertEquals("Author" + result.getId(), result.getAuthor(), "Author não é igual ao Mock");
        assertEquals("Title" + result.getId(), result.getTitle(), "Title não é igual ao Mock");
        assertEquals(BigDecimal.ONE.toString(), result.getPrice().toString(), "Price não é igual ao Mock");
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception ex = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.updateBook(null);
                }
        );

        String expectedMessage = "It's not allowed to persist a null Object";
        assertEquals(ex.getMessage(), expectedMessage);
    }

    @Test
    void deletePerson() {
        Book person = input.mockEntity(1);
        person.setId(1L);
        //Mockito direciona a requisição do banco da forma que eu defini (findById(1L) retorna Optional da variavel person)
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.deleteBook(1L);
        verify(repository, times(1)).findById(anyLong()); //Verifica se foi chamado apenas uma vez
        verify(repository, times(1)).deleteById(anyLong()); //Verifica se foi chamado apenas uma vez
        verifyNoMoreInteractions(repository);//Verifica se não há mais interações com o banco
    }

    //Classe que centraliza teste de links para não repetir codigo
    private void assertLink(Links links, String rel, String hrefEndsWith, String type) {
        assertTrue(
                links.stream().anyMatch(link ->
                        link.getRel().value().equals(rel)
                                && link.getHref().endsWith(hrefEndsWith)
                                && link.getType().equals(type)
                ),
                () -> "Link não encontrado em: rel= " + rel + ", type= " + type
        );

    }
}