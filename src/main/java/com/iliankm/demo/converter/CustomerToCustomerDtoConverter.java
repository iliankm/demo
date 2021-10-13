package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.Converter;
import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * Converter from {@link Customer} to {@link CustomerDto}.
 */
@Component
class CustomerToCustomerDtoConverter implements Converter<Customer, CustomerDto> {

    @Override
    public CustomerDto convert(Customer source) {
        return CustomerDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
