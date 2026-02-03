package br.com.gabrielmaran.pessoa.integrationtest.controllers.withyaml;

import br.com.gabrielmaran.pessoa.config.TestConfigs;
import br.com.gabrielmaran.pessoa.integrationtest.controllers.withyaml.mapper.YAMLMapper;
import br.com.gabrielmaran.pessoa.integrationtest.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.integrationtest.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerYAMLTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;
    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        objectMapper = new YAMLMapper();
        person = new PersonDTO();
        mockPerson();
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        setCurrentSpecification();
        var content =
                given().config(
                RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", person.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .as(PersonDTO.class, objectMapper);

        person = content;

        assertNotNull(person.getId());
        assertTrue(person.getId() > 0);

        assertEquals("FIRSTNAME-TESTE-UPDATE", person.getFirstName());
        assertEquals("LASTNAME-TESTE", person.getLastName());
        assertEquals("ADDRESS-TESTE", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person .getEnabled());
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        setCurrentSpecification();
        var content =
                given().config(
                                RestAssuredConfig.config()
                                        .encoderConfig(EncoderConfig.encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(person, objectMapper)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                .as(PersonDTO.class, objectMapper);

        person = content;

        assertNotNull(person.getId());
        assertTrue(person.getId() > 0);

        assertEquals("FIRSTNAME-TESTE", person.getFirstName());
        assertEquals("LASTNAME-TESTE", person.getLastName());
        assertEquals("ADDRESS-TESTE", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person .getEnabled());
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setFirstName("FIRSTNAME-TESTE-UPDATE");
        setCurrentSpecification();
        var content =
                given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(person, objectMapper)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                .as(PersonDTO.class, objectMapper);

        person = content;

        assertNotNull(person.getId());
        assertTrue(person.getId() > 0);

        assertEquals("FIRSTNAME-TESTE-UPDATE", person.getFirstName());
        assertEquals("LASTNAME-TESTE", person.getLastName());
        assertEquals("ADDRESS-TESTE", person.getAddress());
        assertEquals("Male", person.getGender());
        assertTrue(person .getEnabled());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {
        setCurrentSpecification();
        var content =
                given().config(
                                RestAssuredConfig.config()
                                        .encoderConfig(EncoderConfig.encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                        .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .pathParam("id", person.getId())
                .when()
                    .patch("{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(PersonDTO.class, objectMapper);

        person = content;

        assertNotNull(content.getId());
        assertTrue(content.getId() > 0);

        assertEquals("FIRSTNAME-TESTE-UPDATE", content.getFirstName());
        assertEquals("LASTNAME-TESTE", content.getLastName());
        assertEquals("ADDRESS-TESTE", content.getAddress());
        assertEquals("Male", content.getGender());
        assertFalse(content.getEnabled());
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
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(PersonDTO[].class, objectMapper);

        List<PersonDTO> peopleSeearched = Arrays.asList(content);
        person = peopleSeearched.getFirst();
        assertNotNull(person.getId());
        assertTrue(person.getId() > 0);

        assertEquals("Pessoa 0", person.getFirstName());
        assertEquals("Sobrenome 0", person.getLastName());
        assertEquals("Lugar 0", person.getAddress());
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