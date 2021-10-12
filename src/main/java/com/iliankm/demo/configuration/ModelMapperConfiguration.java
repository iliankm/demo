package com.iliankm.demo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

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
