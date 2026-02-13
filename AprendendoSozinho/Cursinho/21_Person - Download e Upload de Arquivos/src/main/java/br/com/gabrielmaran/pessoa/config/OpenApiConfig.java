package br.com.gabrielmaran.pessoa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // @Bean referencia um objeto que é gerenciado e montado pelo container do Spring
    // Por exemplo, se eu criar um objeto Carro e dar @Autowired, o spring vai procurar dentro dos
        // seus gerenciamentos como é o complemento comum/primario para criação deste objeto
        // se eu criar uma classe que retorna Carro e der @Bean nela
        // e der um @Autowired em uma classe que recebe Carro, ele vai pegar de dentro do seu gerenciamento o complemento
    @Bean
    OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API RESFTFull")
                        .version("v1")
                        .description("REST API's RESFTFull from 0 with Java Spring Boot, Kubernets and Docker")
                        .termsOfService("") // Termos e serviçoes da API
                        .license(new License()
                                .name("") //Nome da licensa
                                .url("") //URL da licensa
                        )
                );
    }
}
