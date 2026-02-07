package br.com.gabrielmaran.pessoa.integrationtest.controllers.withjson;

import br.com.gabrielmaran.pessoa.config.TestConfigs;
import br.com.gabrielmaran.pessoa.integrationtest.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.integrationtest.dto.wrapper.json.WrapperPersonDTO;
import br.com.gabrielmaran.pessoa.integrationtest.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJSONTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //Ignora campos a mais no JSON
        person = new PersonDTO();
        mockPerson();
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        setCurrentSpecification();
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        PersonDTO personSearched = objectMapper.readValue(content, PersonDTO.class);
        person = personSearched;

        assertNotNull(personSearched.getId());
        assertTrue(personSearched.getId() > 0);

        assertEquals("FIRSTNAME-TESTE-UPDATE", personSearched.getFirstName());
        assertEquals("LASTNAME-TESTE", personSearched.getLastName());
        assertEquals("ADDRESS-TESTE", personSearched.getAddress());
        assertEquals("Male", personSearched.getGender());
        assertTrue(personSearched .getEnabled());
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        setCurrentSpecification();
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("FIRSTNAME-TESTE", createdPerson.getFirstName());
        assertEquals("LASTNAME-TESTE", createdPerson.getLastName());
        assertEquals("ADDRESS-TESTE", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson .getEnabled());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setFirstName("FIRSTNAME-TESTE-UPDATE");
        setCurrentSpecification();
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(person)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("FIRSTNAME-TESTE-UPDATE", createdPerson.getFirstName());
        assertEquals("LASTNAME-TESTE", createdPerson.getLastName());
        assertEquals("ADDRESS-TESTE", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertTrue(createdPerson .getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {
        setCurrentSpecification();
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", person.getId())
                .when()
                    .patch("{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("FIRSTNAME-TESTE-UPDATE", createdPerson.getFirstName());
        assertEquals("LASTNAME-TESTE", createdPerson.getLastName());
        assertEquals("ADDRESS-TESTE", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {
        setCurrentSpecification();
        given(specification)
                .pathParam("id", person.getId())
                .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {
        setCurrentSpecification();
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 3, "size", 12, "directio", "asc")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();
        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> peopleSeearched = wrapper.getEmbedded().getPeople();
        person = peopleSeearched.getFirst();

        assertNotNull(person.getId());
        assertTrue(person.getId() > 0);

        assertEquals("Ambros", person.getFirstName());
        assertEquals("Levitt", person.getLastName());
        assertEquals("3 Bunker Hill Trail", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person.getEnabled());
    }



    private static void mockPerson(){
        person.setFirstName("FIRSTNAME-TESTE");
        person.setLastName("LASTNAME-TESTE");
        person.setAddress("ADDRESS-TESTE");
        person.setGender("Male");
        person.setEnabled(true);
    }

    private void setCurrentSpecification(){
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_8080)
                .setBasePath("/api/pessoa/v1") //Path do teste
                .setPort(TestConfigs.SERVER_PORT) //Porta
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de requisição
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de resposta
                .build();
    }


}