package br.com.gabrielmaran.pessoa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    //Criado com objetivo de filtrar dados sensiveis no JSON, devido a configuração não manual do DTO, optei por este metodo
    //Filtra os campos (nomes exatos) que eu adicionar no filtro
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filters = new SimpleFilterProvider().
                addFilter("PersonSensitiveFilter",
                        SimpleBeanPropertyFilter.serializeAllExcept("sensitiveData", "id")); //Adiciona aq e vai filtrar no JSON
        mapper.setFilterProvider(filters);

        return mapper;
    }
}
