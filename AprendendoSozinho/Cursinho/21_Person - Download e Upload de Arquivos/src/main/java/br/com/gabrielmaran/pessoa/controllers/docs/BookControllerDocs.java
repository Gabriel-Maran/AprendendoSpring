package br.com.gabrielmaran.pessoa.controllers.docs;

import br.com.gabrielmaran.pessoa.data.dto.BookDTO;
import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// O melhor era criar um annotation para isto, mas ainda não me aprofundei tanto para isso
public interface BookControllerDocs {
    @Operation(
            summary = "Find Book By ID", tags = {"Books"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "505", content = {@Content})
            })
    ResponseEntity<BookDTO> findById(@PathVariable("id") Long id);

    @Operation(
            summary = "Find All Books", tags = {"Books"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
                                    )
                            }
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "505", content = {@Content})
            })
    ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Create a Book", tags = {"Books"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "505", content = {@Content})
            })
    ResponseEntity<BookDTO> createBook(@RequestBody BookDTO pessoa);

    @Operation(
            summary = "Update a Book", tags = {"Books"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "505", content = {@Content})
            })
    ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO person);

    @Operation(
            summary = "Delete a Book", tags = {"Books"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "505", content = {@Content})
            })
    ResponseEntity<?> deleteBook(@PathVariable("id") Long id);
}
