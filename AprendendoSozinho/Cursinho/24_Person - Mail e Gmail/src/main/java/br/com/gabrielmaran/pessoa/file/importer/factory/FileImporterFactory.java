package br.com.gabrielmaran.pessoa.file.importer.factory;

import br.com.gabrielmaran.pessoa.exception.BadRequestException;
import br.com.gabrielmaran.pessoa.file.importer.contract.FileImporter;
import br.com.gabrielmaran.pessoa.file.importer.impl.CSVImporter;
import br.com.gabrielmaran.pessoa.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    private static final Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) throws Exception{
        if (fileName.endsWith("xlsx")){
            return context.getBean(XlsxImporter.class);
            //return new XlsxImporter();
        }else if(fileName.endsWith("csv")){
            return context.getBean(CSVImporter.class);
            //return new CSVImporter();
        }else{
            throw new BadRequestException("Invalid File Format! Try CSV ou Xlsx");
        }
    }
}
