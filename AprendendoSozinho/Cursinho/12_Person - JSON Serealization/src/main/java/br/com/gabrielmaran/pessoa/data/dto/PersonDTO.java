package br.com.gabrielmaran.pessoa.data.dto;

import br.com.gabrielmaran.pessoa.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//@JsonPropertyOrder({"id", "first_name", "last_name", "gender", "address", "phoneNumber"}) //Usado para ordenar os dados do JSON da maneira preferida
@JsonFilter("PersonSensitiveFilter") //Passando o filtro criado em config, que vai ser usado para filtrar dados sensiveis
public class PersonDTO implements Serializable {

    private static  final Long serialVersionUID = 1L;
    private Long id;

    //@JsonProperty("first_name") //Troca o nome dele no JSON final (usado como value, caso tenha mais properties)
    private String firstName;

    //@JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String lastName;

    private String address;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    //@JsonIgnore //Faz literalmente oq o nome diz, ignora no arquivo JSON gerado este campo
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;


    public PersonDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) object;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getBirthDate(), personDTO.getBirthDate()) && Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber()) && Objects.equals(getGender(), personDTO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getBirthDate(), getPhoneNumber(), getGender());
    }
}
