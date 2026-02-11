package br.com.gabrielmaran.pessoa.integrationtest.dto.wrapper.yaml.book;

import br.com.gabrielmaran.pessoa.integrationtest.dto.BookDTO;
import br.com.gabrielmaran.pessoa.integrationtest.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class PagedModelBookYAML {
    private static final Long serialVersonUID = 1L;

    @XmlElement(name = "content")
    public List<BookDTO> content;

    public PagedModelBookYAML() {}

    public List<BookDTO> getContent() {
        return content;
    }

    public void setContent(List<BookDTO> content) {
        this.content = content;
    }
}
