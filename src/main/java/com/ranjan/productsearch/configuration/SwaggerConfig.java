package com.ranjan.productsearch.configuration;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;




@Configuration

public class SwaggerConfig {
    
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("DocuSecure API")
                        .description("API for secure Document Storage")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Ranjanabha Bhattacharya")
                                .url("https://ranjanabha.com")
                                .email("ranjanabha@gmail.com")
                        )

                )
                .externalDocs(new ExternalDocumentation()
                        .description("More API Documentation")
                        .url("https://ranjanabha-docs-url.com"));
    }

   
}
