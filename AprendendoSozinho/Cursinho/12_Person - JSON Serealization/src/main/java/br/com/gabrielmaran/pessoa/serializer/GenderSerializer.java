package br.com.gabrielmaran.pessoa.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<String> {
    //Basicamente troca o Male para M e Female para F
    //É usado pelo DTO de Person
    @Override
    public void serialize(String gender, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String formaterGender = "male".equalsIgnoreCase(gender) ? "M" : "F";
        jsonGenerator.writeString(formaterGender);
    }
}
