package br.com.gabrielmaran.pessoa.controllers;

import br.com.gabrielmaran.pessoa.controllers.docs.FileControllerDocs;
import br.com.gabrielmaran.pessoa.data.dto.UploadFileResponseDTO;
import br.com.gabrielmaran.pessoa.service.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/file/v1")
@Tag(name = "FileStorage", description = "Endpoints that manage files")
public class FileStorageController implements FileControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    private FileStorageService service;

    @Override
    @PostMapping("/uploadFile")
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = service.storeFile(file);

        //http://localhost:8080/api/file/v1/downloadFile/fileName.docx
        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @Override
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseDTO> uploadMultipleFiles(@RequestParam("files")MultipartFile[] files) {
        List<UploadFileResponseDTO> responses = new ArrayList<>();
        Arrays.stream(files).forEach(file ->{
            UploadFileResponseDTO uploadFileResponseDTO = uploadFile(file);
            responses.add(uploadFileResponseDTO);
        });
        return responses ;
    }

    @Override
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName);
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception e){
            logger.error("Could not determine file type!");
        }
        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment=\""+resource.getFilename()+"\"")
                .body(resource);
    }
}
