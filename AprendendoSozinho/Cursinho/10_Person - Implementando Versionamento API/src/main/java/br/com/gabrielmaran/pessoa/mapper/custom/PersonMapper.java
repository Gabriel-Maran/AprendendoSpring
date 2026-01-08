package br.com.gabrielmaran.pessoa.mapper.custom;

import br.com.gabrielmaran.pessoa.data.dto.v2.PersonDTOV2;
import br.com.gabrielmaran.pessoa.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonMapper {

    public static PersonDTOV2 convertEntityToDTO(Person person){
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDate(person.getBirthDate());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        return dto;
    }

    public static List<PersonDTOV2> convertEntitiesToDTO(List<Person> people){
        ArrayList<PersonDTOV2> peopleConverted = new ArrayList<PersonDTOV2>();
        for (Person person : people){
            peopleConverted.add(convertEntityToDTO(person));
        }
        return peopleConverted;
    }
}
