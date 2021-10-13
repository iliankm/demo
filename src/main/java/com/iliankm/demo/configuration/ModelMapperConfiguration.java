package com.iliankm.demo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for {@link ModelMapper}.
 */
@Configuration
public class ModelMapperConfiguration {

    /**
     * Producer method for {@link ModelMapper}.
     *
     * @return modelMapper bean instance
     */
    @Bean
    public ModelMapper modelMapper() {
        //final ModelMapper modelMapper = new ModelMapper();
        //modelMapper.getConfiguration()
        //.setMatchingStrategy(MatchingStrategies.STANDARD);
        //.setFieldMatchingEnabled(true)
        //.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return new ModelMapper();
    }
}
