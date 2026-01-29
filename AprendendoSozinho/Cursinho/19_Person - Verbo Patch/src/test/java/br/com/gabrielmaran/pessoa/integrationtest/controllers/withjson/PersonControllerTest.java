package br.com.gabrielmaran.pessoa.integrationtest.controllers.withjson;

import br.com.gabrielmaran.pessoa.config.TestConfigs;
import br.com.gabrielmaran.pessoa.integrationtest.dto.PersonDTO;
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

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //Ignora campos a mais no JSON
        person = new PersonDTO();
    }

    @Test
    @Order(3)
    void findByIdWithCorrectOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_8080)
                .setBasePath("/api/pessoa/v1") //Path do teste
                .setPort(TestConfigs.SERVER_PORT) //Porta
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de requisição
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de resposta
                .build();
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
        assertNotNull(personSearched.getFirstName());
        assertNotNull(personSearched.getLastName());
        assertNotNull(personSearched.getAddress());
        assertNotNull(personSearched.getGender());
        assertTrue(personSearched.getId() > 0);

        assertEquals("FIRSTNAME-TESTE", personSearched.getFirstName());
        assertEquals("LASTNAME-TESTE", personSearched.getLastName());
        assertEquals("ADDRESS-TESTE", personSearched.getAddress());
        assertEquals("Male", personSearched.getGender());
    }

    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_8081)
                .setBasePath("/api/pessoa/v1") //Path do teste
                .setPort(TestConfigs.SERVER_PORT) //Porta
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de requisição
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de resposta
                .build();
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }


    @Test
    @Order(1)
    void createPersonWithCorrectOrigin() throws JsonProcessingException {
        mockPerson();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_8080)
                .setBasePath("/api/pessoa/v1") //Path do teste
                .setPort(TestConfigs.SERVER_PORT) //Porta
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de requisição
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de resposta
                .build();
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("FIRSTNAME-TESTE", createdPerson.getFirstName());
        assertEquals("LASTNAME-TESTE", createdPerson.getLastName());
        assertEquals("ADDRESS-TESTE", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(2)
    void createPersonWithWrongOrigin() throws JsonProcessingException {
        mockPerson();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_8081)
                .setBasePath("/api/pessoa/v1") //Path do teste
                .setPort(TestConfigs.SERVER_PORT) //Porta
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de requisição
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de resposta
                .build();
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    private void mockPerson(){
        person.setFirstName("FIRSTNAME-TESTE");
        person.setLastName("LASTNAME-TESTE");
        person.setAddress("ADDRESS-TESTE");
        person.setGender("Male");
    }


}