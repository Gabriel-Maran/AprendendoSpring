package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.config.FileStorageConfig;
import br.com.gabrielmaran.pessoa.controllers.FileStorageController;
import br.com.gabrielmaran.pessoa.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUpload_dir())
                .toAbsolutePath()
                .normalize();
        this.fileStorageLocation = path;
        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch (IOException e) {
            logger.error("Could not create the directory where files will be stored!");
            throw new FileStorageException("Could not create the directory where files will be stored!", e);
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if(fileName.contains("..")){
                logger.error("Sorry, file name contains a invalid Path Sequence {}", fileName);
                throw new FileStorageException("Sorry, file name contains a invalid Path Sequence " + fileName);
            }
            logger.info("Save file in Disk");
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch (Exception e){
            logger.error("Could not store file {}. Please try again", fileName);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again", e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize().normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }
            throw new FileStorageException("File not found with name " + fileName);
        }catch (Exception e){
            logger.error("File not found with name {}", fileName);
            throw new FileStorageException("File not found with name " + fileName);
        }
    }
}
