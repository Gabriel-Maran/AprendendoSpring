package br.com.gabrielmaran.pessoa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // @Bean é usado para declarar um comportamento de outro sistema no nosso proprio sistema
        // Ele é feito nessa classe com a anotation Configuration, pois essa notação faz com que a classe carregue no começo
        // Assim, faz com que já tome a configuração e instancie oq é necessario
        // @Bean é usada em classes de configuração
    @Value("${cors.originPatterns:http://localhost:8080}")
    private String corsOriginPatterns = "";

    //Abilita os CORS para a aplicação de forma global, podemos habilitar por metodo, controller ou assim, de maneira global
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOriginis = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                .allowedOrigins(allowedOriginis)
                .allowedMethods("*") // allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Via extension era usado até o SpringBoot 2.6 (Ex: relatorio.xml ou usuario/1.json )
            // Chamava .xml ou outra extensão e a config pre interpretava isso

        // Via query param relatorio?mediaType=xml ou usuario/1?mediaType=xml
        //configurer.favorParameter(true)
        //        .parameterName("mediaType")
        //        .ignoreAcceptHeader(true)
        //        .useRegisteredExtensionsOnly(false)
        //        .defaultContentType(MediaType.APPLICATION_JSON)
        //        .mediaType("json", MediaType.APPLICATION_JSON)
        //        .mediaType("xml", MediaType.APPLICATION_XML);

        // Via header param 'Accept to application/xml' ou 'Accept to application/json' em headers da requisição
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);
    }

}
