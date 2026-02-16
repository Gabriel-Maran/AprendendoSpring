package br.com.gabrielmaran.pessoa.controllers;

import br.com.gabrielmaran.pessoa.controllers.docs.EmailControllerDocs;
import br.com.gabrielmaran.pessoa.data.dto.request.EmailRequestDTO;
import br.com.gabrielmaran.pessoa.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email/v1")
@Tag(name = "Books", description = "Endpoints for Sending Emails")
public class EmailController implements EmailControllerDocs {

    @Autowired
    private EmailService emailService;

    @PostMapping
    @Override
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
        emailService.sendSimpleEmail(emailRequestDTO.getTo(), emailRequestDTO.getSubject(), emailRequestDTO.getSubject());
        return new ResponseEntity<>("Email sent with sucess", HttpStatus.OK);
    }

    @PostMapping(value = "/withAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam("emailRequest") String emailRequestJSON,
            @RequestParam("attachment") MultipartFile attachment
    ) {
        emailService.sendEmailWIthAttachment(emailRequestJSON, attachment);
        return new ResponseEntity<>("Email with attachment send successfully!", HttpStatus.OK);
    }
}
