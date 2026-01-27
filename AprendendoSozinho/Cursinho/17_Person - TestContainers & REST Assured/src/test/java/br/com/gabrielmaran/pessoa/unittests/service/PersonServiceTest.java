package br.com.gabrielmaran.pessoa.unittests.service;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.exception.RequiredObjectIsNullException;
import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.repository.PersonRepository;
import br.com.gabrielmaran.pessoa.service.PersonService;
import br.com.gabrielmaran.pessoa.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Links;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Tudo que for testado desta vez só existirá esta vez
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @Mock
    PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findById(1L);
        assertNotNull(result, "Resultado null");
        assertNotNull(result.getId(), "Id null");
        assertNotNull(result.getLinks(), "Sem links/links nulls");
        var links = result.getLinks();

        //Teste de links
        assertLink(links, "self", "/api/pessoa/v1/" + person.getId(), "GET");
        assertLink(links, "findPeople", "/api/pessoa/v1", "GET");
        assertLink(links, "create", "/api/pessoa/v1", "POST");
        assertLink(links, "edit", "/api/pessoa/v1", "PUT");
        assertLink(links, "delete", "/api/pessoa/v1/" + person.getId(), "DELETE");

        //Teste de variaveis do Objeto
        assertEquals("Address Test1", result.getAddress(), "Endereço incorreto ao comparar com o Mock");
        assertEquals("Female", result.getGender(), "Gênero' incorreto ao comparar com o Mock");
        assertEquals("Last Name Test1", result.getLastName(), "Ultimo nome incorreto ao comparar com o Mock");
        assertEquals("First Name Test1", result.getFirstName(), "Primeiro nome incorreto ao comparar com o Mock");
    }

    @Test
    void findAll() {
        List<Person> people = input.mockEntityList();
        for (Person person : people) {
            when(repository.findById(person.getId())).thenReturn(Optional.of(person));
            var result = service.findById(person.getId());
            assertNotNull(result, "Resultado null");
            assertNotNull(result.getId(), "Id null");
            assertNotNull(result.getLinks(), "Sem links/links nulls");
            var links = result.getLinks();

            //Teste de links
            assertLink(links, "self", "/api/pessoa/v1/" + person.getId(), "GET");
            assertLink(links, "findPeople", "/api/pessoa/v1", "GET");
            assertLink(links, "create", "/api/pessoa/v1", "POST");
            assertLink(links, "edit", "/api/pessoa/v1", "PUT");
            assertLink(links, "delete", "/api/pessoa/v1/" + person.getId(), "DELETE");

            //Teste de variaveis do Objeto
            assertEquals("Address Test" + person.getId(), result.getAddress(), "Endereço incorreto ao comparar com o Mock");
            assertEquals(((person.getId() % 2)==0) ? "Male" : "Female", result.getGender(), "Gênero' incorreto ao comparar com o Mock");
            assertEquals("Last Name Test" + person.getId(), result.getLastName(), "Ultimo nome incorreto ao comparar com o Mock");
            assertEquals("First Name Test" + person.getId(), result.getFirstName(), "Primeiro nome incorreto ao comparar com o Mock");
        }
    }

    @Test
    void createPerson() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        PersonDTO dto = input.mockDTO(1);

        when(repository.save(person)).thenReturn(persisted);
        var result = service.createPerson(dto);
        assertNotNull(result, "Resultado null");
        assertNotNull(result.getId(), "Id null");
        assertNotNull(result.getLinks(), "Sem links/links nulls");
        var links = result.getLinks();

        //Teste de links
        assertLink(links, "self", "/api/pessoa/v1/" + person.getId(), "GET");
        assertLink(links, "findPeople", "/api/pessoa/v1", "GET");
        assertLink(links, "create", "/api/pessoa/v1", "POST");
        assertLink(links, "edit", "/api/pessoa/v1", "PUT");
        assertLink(links, "delete", "/api/pessoa/v1/" + person.getId(), "DELETE");

        //Teste de variaveis do Objeto
        assertEquals("Address Test1", result.getAddress(), "Endereço incorreto ao comparar com o Mock");
        assertEquals("Female", result.getGender(), "Gênero' incorreto ao comparar com o Mock");
        assertEquals("Last Name Test1", result.getLastName(), "Ultimo nome incorreto ao comparar com o Mock");
        assertEquals("First Name Test1", result.getFirstName(), "Primeiro nome incorreto ao comparar com o Mock");
    }

    @Test
    void testCreateWithNullPerson() {
        Exception ex = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.createPerson(null);
                }
        );

        String expectedMessage = "It's not allowed to persist a null Object";
        assertEquals(ex.getMessage(), expectedMessage);
    }

    @Test
    void updatePerson() {
        Person person = input.mockEntity(1);
        Person persisted = person;
        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(persisted);

        var result = service.updatePerson(dto);
        assertNotNull(result, "Resultado null");
        assertNotNull(result.getId(), "Id null");
        assertNotNull(result.getLinks(), "Sem links/links nulls");
        var links = result.getLinks();

        //Teste de links
        assertLink(links, "self", "/api/pessoa/v1/" + person.getId(), "GET");
        assertLink(links, "findPeople", "/api/pessoa/v1", "GET");
        assertLink(links, "create", "/api/pessoa/v1", "POST");
        assertLink(links, "edit", "/api/pessoa/v1", "PUT");
        assertLink(links, "delete", "/api/pessoa/v1/" + person.getId(), "DELETE");

        //Teste de variaveis do Objeto
        assertEquals("Address Test1", result.getAddress(), "Endereço incorreto ao comparar com o Mock");
        assertEquals("Female", result.getGender(), "Gênero' incorreto ao comparar com o Mock");
        assertEquals("Last Name Test1", result.getLastName(), "Ultimo nome incorreto ao comparar com o Mock");
        assertEquals("First Name Test1", result.getFirstName(), "Primeiro nome incorreto ao comparar com o Mock");
    }

    @Test
    void testUpdateWithNullPerson() {
        Exception ex = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.updatePerson(null);
                }
        );

        String expectedMessage = "It's not allowed to persist a null Object";
        assertEquals(ex.getMessage(), expectedMessage);
    }

    @Test
    void deletePerson() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        //Mockito direciona a requisição do banco da forma que eu defini (findById(1L) retorna Optional da variavel person)
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.deletePerson(1L);
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