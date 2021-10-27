package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.Converter;
import com.iliankm.demo.dto.CustomerCreateDto;
import com.iliankm.demo.service.customer.CustomerCreateData;
import org.springframework.stereotype.Component;

/**
 * Converter from {@link CustomerCreateDto} to {@link CustomerCreateData}.
 */
@Component
class CustomerCreateDtoToCustomerCreateDataConverter
        implements Converter<CustomerCreateDto, CustomerCreateData> {

    @Override
    public CustomerCreateData convert(CustomerCreateDto source) {
        return new CustomerCreateData(source.getFirstName(), source.getLastName());
    }
}
