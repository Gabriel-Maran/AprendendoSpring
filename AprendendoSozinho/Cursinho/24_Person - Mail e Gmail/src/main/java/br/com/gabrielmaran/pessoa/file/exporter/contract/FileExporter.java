package br.com.gabrielmaran.pessoa.file.exporter.contract;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileExporter {
    Resource exportFile(List<PersonDTO> people) throws Exception;
}