package br.com.gabrielmaran.pessoa.integrationtest.dto.wrapper.yaml.person;

import br.com.gabrielmaran.pessoa.integrationtest.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class PagedModelPersonYAML {
    private static final Long serialVersonUID = 1L;

    @XmlElement(name = "content")
    public List<PersonDTO> content;

    public PagedModelPersonYAML() {}

    public List<PersonDTO> getContent() {
        return content;
    }

    public void setContent(List<PersonDTO> content) {
        this.content = content;
    }
}
