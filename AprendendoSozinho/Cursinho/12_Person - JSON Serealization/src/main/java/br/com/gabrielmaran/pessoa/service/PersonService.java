package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import br.com.gabrielmaran.pessoa.exception.ResourceNotFoundException;
import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseListObjects ;
import static br.com.gabrielmaran.pessoa.mapper.ObjectMapper.parseObject ;
import br.com.gabrielmaran.pessoa.model.Person;
import br.com.gabrielmaran.pessoa.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired // Por pura preguiça de fazer um contrutor
    PersonRepository repository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public PersonDTO findById(Long id) {
        logger.info("Finding one person by id: " + id);
        var entity =  repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all people");
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO createPerson(PersonDTO pessoa) {
        logger.info("Creating one person");
        var entity = parseObject(pessoa, Person.class);
        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTO updatePerson(PersonDTO pessoa) {
        logger.info("Updating one person");
        Person entity = repository.findById(pessoa.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + pessoa.getId() + " id"));
        entity.setFirstName(pessoa.getFirstName());
        entity.setLastName(pessoa.getLastName());
        entity.setBirthDate(pessoa.getBirthDate());
        entity.setPhoneNumber(pessoa.getPhoneNumber());
        entity.setAddress(pessoa.getAddress());
        entity.setGender(pessoa.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting one person");
        repository.deleteById(id);
    }
}
