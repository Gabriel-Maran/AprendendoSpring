package br.com.gabrielmaran.pessoa.file.exporter.factory;

import br.com.gabrielmaran.pessoa.exception.BadRequestException;
import br.com.gabrielmaran.pessoa.file.exporter.MediaTypes;
import br.com.gabrielmaran.pessoa.file.exporter.contract.FileExporter;
import br.com.gabrielmaran.pessoa.file.exporter.impl.CSVExporter;
import br.com.gabrielmaran.pessoa.file.exporter.impl.XlsxExporter;
import br.com.gabrielmaran.pessoa.file.importer.contract.FileImporter;
import br.com.gabrielmaran.pessoa.file.importer.impl.CSVImporter;
import br.com.gabrielmaran.pessoa.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private static final Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception{
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)){
            return context.getBean(XlsxExporter.class);
            //return new XlsxImporter();
        }else if(acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)){
            return context.getBean(CSVExporter.class);
            //return new CSVImporter();
        }else{
            throw new BadRequestException("Invalid File Format! Try CSV ou Xlsx");
        }
    }
}
