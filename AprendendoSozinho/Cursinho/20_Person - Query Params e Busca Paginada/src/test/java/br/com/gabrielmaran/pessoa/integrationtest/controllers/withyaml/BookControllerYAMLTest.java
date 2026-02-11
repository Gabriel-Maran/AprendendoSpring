package br.com.gabrielmaran.pessoa.integrationtest.controllers.withyaml;

import br.com.gabrielmaran.pessoa.config.TestConfigs;
import br.com.gabrielmaran.pessoa.integrationtest.controllers.withyaml.mapper.YAMLMapper;
import br.com.gabrielmaran.pessoa.integrationtest.dto.BookDTO;
import br.com.gabrielmaran.pessoa.integrationtest.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.integrationtest.dto.wrapper.xml.book.PagedModelBookXML;
import br.com.gabrielmaran.pessoa.integrationtest.dto.wrapper.yaml.book.PagedModelBookYAML;
import br.com.gabrielmaran.pessoa.integrationtest.dto.wrapper.yaml.person.PagedModelPersonYAML;
import br.com.gabrielmaran.pessoa.integrationtest.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerYAMLTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;
    private static BookDTO book;

    @BeforeAll
    static void setUp() {
        objectMapper = new YAMLMapper();
        book = new BookDTO();
        mockBook();
    }
    @Test
    @Order(1)
    void createBook() throws JsonProcessingException {
        setCurrentSpecification();
        var content =
                given().config(
                                RestAssuredConfig.config()
                                        .encoderConfig(EncoderConfig.encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                        .spec(specification)
                        .contentType(MediaType.APPLICATION_YAML_VALUE)
                        .accept(MediaType.APPLICATION_YAML_VALUE)
                            .body(book, objectMapper)
                        .when()
                            .post()
                        .then()
                            .statusCode(200)
                        .contentType(MediaType.APPLICATION_YAML_VALUE)
                        .extract()
                            .body()
                                .as(BookDTO.class, objectMapper);

        BookDTO createdPerson = content;
        book = content;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("AUTHOR-TESTE", book.getAuthor() );
        assertEquals("TITLE-TESTE", book.getTitle() );
        assertEquals(
            Date.from(
                LocalDate.now()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
            ), book.getLaunch_date()
        );
        assertEquals(BigDecimal.ONE, book.getPrice() );
    }

    @Test
    @Order(2)
    void updateBook() throws JsonProcessingException {
        book.setAuthor("AUTHOR-TESTE-UPDATE");
        setCurrentSpecification();
        var content =
                given().config(
                                RestAssuredConfig.config()
                                        .encoderConfig(EncoderConfig.encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                        .spec(specification)
                        .contentType(MediaType.APPLICATION_YAML_VALUE)
                        .accept(MediaType.APPLICATION_YAML_VALUE)
                        .body(book, objectMapper)
                        .when()
                        .put()
                        .then()
                        .statusCode(200)
                        .contentType(MediaType.APPLICATION_YAML_VALUE)
                        .extract()
                        .body()
                        .as(BookDTO.class, objectMapper);

        BookDTO updatedBook = content;
        book = updatedBook;

        assertNotNull(updatedBook.getId());
        assertTrue(updatedBook.getId() > 0);

        assertEquals("AUTHOR-TESTE-UPDATE", book.getAuthor() );
        assertEquals("TITLE-TESTE", book.getTitle() );
        assertEquals(
                Date.from(
                        LocalDate.now()
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                ), book.getLaunch_date()
        );
        assertEquals(BigDecimal.ONE, book.getPrice() );
    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        setCurrentSpecification();
        var content =
                given().config(
                                RestAssuredConfig.config()
                                        .encoderConfig(EncoderConfig.encoderConfig()
                                                .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", book.getId())
                .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .as(BookDTO.class, objectMapper);
        BookDTO updatedBook = content;
        book = updatedBook;

        assertNotNull(updatedBook.getId());
        assertTrue(updatedBook.getId() > 0);

        assertEquals("AUTHOR-TESTE-UPDATE", book.getAuthor() );
        assertEquals("TITLE-TESTE", book.getTitle() );
        assertEquals(
                Date.from(
                        LocalDate.now()
                                .atStartOfDay(ZoneId.systemDefault())
                                .toInstant()
                ), book.getLaunch_date()
        );
        assertEquals(BigDecimal.ONE.stripTrailingZeros(), book.getPrice().stripTrailingZeros());
    }

    @Test
    @Order(4)
    void findAll() throws JsonProcessingException {
        setCurrentSpecification();
        var content =
                given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT)))
                .spec(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParam("page", 1, "size", 2, "direction", "asc")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(PagedModelBookYAML.class, objectMapper);

        List<BookDTO> booksSearched = content.getContent();
        book = booksSearched.getFirst();

        assertNotNull(book.getId());
        assertTrue(book.getId() > 0);

        assertEquals("AUTHOR-TESTE-UPDATE", book.getAuthor() );
        assertEquals("TITLE-TESTE", book.getTitle() );
        assertEquals("Wed Feb 11 00:00:00 BRT 2026", book.getLaunch_date().toString());
        assertEquals(BigDecimal.valueOf(1), BigDecimal.valueOf(book.getPrice().intValueExact()));
    }

    @Test
    @Order(5)
    void deleteBook() {
        setCurrentSpecification();
        given(specification)
                .pathParam("id", book.getId())
                .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);
    }

    private static void mockBook(){
        book.setId(1L);
        book.setAuthor("AUTHOR-TESTE");
        book.setTitle("TITLE-TESTE");
        book.setLaunch_date(
                Date.from(
                        LocalDate.now()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                )
        );
        book.setPrice(BigDecimal.ONE);
    }

    private void setCurrentSpecification(){
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_8080)
                .setBasePath("/api/book/v1") //Path do teste
                .setPort(TestConfigs.SERVER_PORT) //Porta
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de requisição
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) //Nivel de detalhe do log de resposta
                .build();
    }
}