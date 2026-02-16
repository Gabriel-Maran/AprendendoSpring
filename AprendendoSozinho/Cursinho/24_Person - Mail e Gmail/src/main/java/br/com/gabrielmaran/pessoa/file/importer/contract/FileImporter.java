package br.com.gabrielmaran.pessoa.file.importer.contract;

import br.com.gabrielmaran.pessoa.data.dto.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    List<PersonDTO> importFile(InputStream inputStream) throws Exception;


}
