package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.repository.PersonRepository;
import br.com.gabrielmaran.pessoa.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    }

    @Test
    void createPerson() {

    }

    @Test
    void updatePerson() {

    }

    @Test
    void deletePerson() {

    }

    //Classe que centraliza teste de links para não repetir codigo
    private void assertLink(Links links, String rel, String hrefEndsWith, String type){
        assertTrue(
                links.stream().anyMatch(link ->
                        link.getRel().value().equals(rel)
                        && link.getHref().endsWith(hrefEndsWith)
                        && link.getType().equals(type)
                ),
                () -> "Link não encontrado em: rel= " + rel + ", type= "+type
        );

    }
}