package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.config.EmailConfig;
import br.com.gabrielmaran.pessoa.data.dto.request.EmailRequestDTO;
import br.com.gabrielmaran.pessoa.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailConfig emailConfigs;

    public void sendSimpleEmail(String to, String subject, String body){
        emailSender
                .to(to)
                .withSubject(subject)
                .withMessage(body)
                .send(emailConfigs);
    }

    public void sendEmailWIthAttachment(String emailRequestJSON, MultipartFile attachment){
        File tempFile = null;
        try {
            EmailRequestDTO emailRequestDTO = new ObjectMapper().readValue(emailRequestJSON, EmailRequestDTO.class);
            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());
            attachment.transferTo(tempFile);
            emailSender
                    .to(emailRequestDTO.getTo())
                    .withSubject(emailRequestDTO.getSubject())
                    .withMessage(emailRequestDTO.getBody())
                    .attach(tempFile.getAbsolutePath())
                    .send(emailConfigs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error reading email request", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading attachment", e);
        }finally {
            if(tempFile != null && tempFile.exists()) tempFile.delete();
        }
    }
}
