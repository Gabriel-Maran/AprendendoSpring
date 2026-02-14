package br.com.gabrielmaran.pessoa.repository;

import br.com.gabrielmaran.pessoa.config.TestConfigs;
import br.com.gabrielmaran.pessoa.integrationtest.testcontainers.AbstractIntegrationTest;
import br.com.gabrielmaran.pessoa.model.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class) //Carrega o Spring no ambiente de teste
@DataJpaTest() //Configura o teste para trabalhar com JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE ) //Configurado assim, mexe no banco de dados real
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Order dos testes
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;
    private static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person();
    }

    @Test
    @Order(1)
    void findPeopleByName() {
        PageRequest pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.ASC, "firstName"));
        person = repository.findPeopleByName("111", pageable).getContent().getFirst();
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Pessoa 111", person.getFirstName());
        assertEquals("Sobrenome 0", person.getLastName());
        assertEquals("Male", person.getGender());
        assertEquals("Lugar 0", person.getAddress());
        assertTrue(person.isEnabled());
    }

    @Test
    @Order(2)
    void disablePerson() {
        Long id = person.getId();
        repository.disablePerson(id);

        var result = repository.findById(id);
        person = result.get();
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Pessoa 111", person.getFirstName());
        assertEquals("Sobrenome 0", person.getLastName());
        assertEquals("Male", person.getGender());
        assertEquals("Lugar 0", person.getAddress());
        assertFalse(person.isEnabled());
    }

    private static void mockPerson(){
        person.setFirstName("FIRSTNAME-TESTE");
        person.setLastName("LASTNAME-TESTE");
        person.setAddress("ADDRESS-TESTE");
        person.setGender("Male");
        person.setEnabled(true);
    }
}