package br.com.gabrielmaran.pessoa.controllers.docs;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.data.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileControllerDocs {

    @Operation(
            summary = "Upload a file", tags = {"Files"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UploadFileResponseDTO.class))
                    ),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})
            })
    UploadFileResponseDTO uploadFile(MultipartFile file);

    @Operation(
            summary = "Upload multiple files", tags = {"Files"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UploadFileResponseDTO.class))
                            )                    ),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content})
            })
    List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] file);

    @Operation(
            summary = "Download a file", tags = {"Files"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Resource.class))
                    ),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = {@Content}),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = {@Content})
            })
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);

}
