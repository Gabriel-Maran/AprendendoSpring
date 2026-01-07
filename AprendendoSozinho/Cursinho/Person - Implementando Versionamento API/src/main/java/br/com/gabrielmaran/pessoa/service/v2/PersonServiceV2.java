package br.com.gabrielmaran.pessoa.service.v2;

import br.com.gabrielmaran.pessoa.data.dto.v2.PersonDTOV2;
import br.com.gabrielmaran.pessoa.exception.ResourceNotFoundException;
import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseListObjects;
import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseObject;
import static br.com.gabrielmaran.pessoa.mapper.custom.PersonMapper.convertEntitiesToDTO;
import static br.com.gabrielmaran.pessoa.mapper.custom.PersonMapper.convertEntityToDTO;

@Service
public class PersonServiceV2 {
    @Autowired // Por pura preguiça de fazer um contrutor
    PersonRepository repository;

    private final Logger logger = LoggerFactory.getLogger(PersonServiceV2.class.getName());

    public PersonDTOV2 findByIdV2(Long id) {
        logger.info("Finding one person by id: " + id);
        var entity =  repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        return convertEntityToDTO(entity);
    }

    public List<PersonDTOV2> findAllV2() {
        logger.info("Finding all people");
        return convertEntitiesToDTO(repository.findAll());
    }

    public PersonDTOV2 createPersonV2(Person pessoa) {
        logger.info("Creating one person");
        return convertEntityToDTO(repository.save(pessoa));
    }

    public PersonDTOV2 updatePersonV2(PersonDTOV2 pessoa) {
        logger.info("Updating one person");
        Person entity = repository.findById(pessoa.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + pessoa.getId() + " id"));
        entity.setId(pessoa.getId());
        entity.setFirstName(pessoa.getFirstName());
        entity.setLastName(pessoa.getLastName());
        entity.setBirthDate(pessoa.getBirthDate());
        entity.setAddress(pessoa.getAddress());
        entity.setGender(pessoa.getGender());

        return convertEntityToDTO(repository.save(entity));
    }

    public void deletePersonV2(Long id) {
        logger.info("Deleting one person");
        repository.deleteById(id);
    }
}
