package br.com.gabrielmaran.pessoa.integrationtest.dto.wrapper.xml;

import br.com.gabrielmaran.pessoa.integrationtest.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class PagedModelPersonXML {
    private static final Long serialVersonUID = 1L;

    @XmlElement(name = "content")
    public List<PersonDTO> content;

    public PagedModelPersonXML() {}

    public List<PersonDTO> getContent() {
        return content;
    }

    public void setContent(List<PersonDTO> content) {
        this.content = content;
    }
}
