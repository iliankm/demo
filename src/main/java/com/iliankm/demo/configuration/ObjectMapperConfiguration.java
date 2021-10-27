package com.iliankm.demo.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration for {@link com.fasterxml.jackson.databind.ObjectMapper}.
 */
@Configuration
public class ObjectMapperConfiguration {

    /**
     * Producer method for {@link ObjectMapper}.
     *
     * @return objectMapper bean instance
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new JsonNullableModule());
    }
}
