package br.com.gabrielmaran.pessoa.service;

import br.com.gabrielmaran.pessoa.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {
    /*
    * Todos os serviços aqui são apenas mocks didáticos
    * Tem como fim entender melhor como funciona um projeto Spring
        * Requisições HTTP, build.....
    */
    private final AtomicLong counter = new AtomicLong();
    private Logger logger =  Logger.getLogger(PersonService.class.getName());

    public Person findById(String id){
        logger.info("Finding one person by id: "+id);
        Person person = new Person();
        return mockPerson(Integer.parseInt(id));
    }

    public List<Person> findAll(){
        logger.info("Finding all people");
        List<Person> pessoas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            pessoas.add(mockPerson(i));
        }
        return pessoas;
    }

    public Person createPerson(Person pessoa){
        logger.info("Creating one person");
        // Banco é responsável pelo Id, estou apenas implementando aqui para não retornar nullo
        pessoa.setId(counter.incrementAndGet());
        return pessoa;
    }

    public Person updatePerson(Person pessoa){
        logger.info("Updating one person");
        //Mesma situação do create
        pessoa.setId(counter.incrementAndGet());
        return pessoa;
    }

    public void deletePerson(String id) {
        logger.info("Deleting one person");
    }

    private Person mockPerson(int index){
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Pessoa "+index );
        person.setLastName("Sobrenome "+index);
        person.setAddress("Lugar "+index);
        person.setGender(index%2 ==1 ? "Male" : "Female");
        return person;
    }

}
