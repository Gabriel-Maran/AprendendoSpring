package br.com.gabrielmaran.pessoa.controllers.docs;


import br.com.gabrielmaran.pessoa.data.dto.request.EmailRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface EmailControllerDocs {

    @Operation(
            summary = "Send an Email", tags = {"Email"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "505", content = {@Content})
            })
    ResponseEntity<String> sendEmail(EmailRequestDTO emailRequestDTO);

    @Operation(
            summary = "Send an Email with Attachment", tags = {"Email"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                    @ApiResponse(description = "Internal Server Error", responseCode = "505", content = {@Content})
            })
    ResponseEntity<String> sendEmailWithAttachment(String emailRequestJSON, MultipartFile multipartFile);


}
