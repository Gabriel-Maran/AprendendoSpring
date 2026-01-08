package br.com.gabrielmaran.pessoa.service.v1;

import br.com.gabrielmaran.pessoa.data.dto.v1.PersonDTOV1;
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
public class PersonServiceV1 {
    @Autowired // Por pura preguiça de fazer um contrutor
    PersonRepository repository;

    private final Logger logger = LoggerFactory.getLogger(PersonServiceV1.class.getName());

    public PersonDTOV1 findById(Long id) {
        logger.info("Finding one person by id: " + id);
        var entity =  repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + id + " id"));
        return parseObject(entity, PersonDTOV1.class);
    }

    public List<PersonDTOV1> findAll() {
        logger.info("Finding all people");
        return parseListObjects(repository.findAll(), PersonDTOV1.class);
    }

    public PersonDTOV1 createPerson(PersonDTOV1 pessoa) {
        logger.info("Creating one person");
        var entity = parseObject(pessoa, Person.class);
        return parseObject(repository.save(entity), PersonDTOV1.class);
    }

    public PersonDTOV1 updatePerson(PersonDTOV1 pessoa) {
        logger.info("Updating one person");
        Person entity = repository.findById(pessoa.getId()).
                orElseThrow(() -> new ResourceNotFoundException("No records find for " + pessoa.getId() + " id"));
        entity.setId(pessoa.getId());
        entity.setFirstName(pessoa.getFirstName());
        entity.setLastName(pessoa.getLastName());
        entity.setAddress(pessoa.getAddress());
        entity.setGender(pessoa.getGender());

        return parseObject(repository.save(entity), PersonDTOV1.class);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting one person");
        repository.deleteById(id);
    }
}
