package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.Converter;
import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.entity.CustomerEntity;
import org.springframework.stereotype.Component;

/**
 * Converter from {@link CustomerEntity} to {@link CustomerDto}.
 */
@Component
class CustomerEntityToCustomerDtoConverter implements Converter<CustomerEntity, CustomerDto> {

    @Override
    public CustomerDto convert(CustomerEntity source) {
        return CustomerDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
