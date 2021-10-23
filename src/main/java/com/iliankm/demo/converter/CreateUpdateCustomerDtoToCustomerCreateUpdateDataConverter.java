package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.Converter;
import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import com.iliankm.demo.service.customer.CustomerCreateUpdateData;
import org.springframework.stereotype.Component;

/**
 * Converter from {@link CreateUpdateCustomerDto} to {@link CustomerCreateUpdateData}.
 */
@Component
class CreateUpdateCustomerDtoToCustomerCreateUpdateDataConverter
        implements Converter<CreateUpdateCustomerDto, CustomerCreateUpdateData> {

    @Override
    public CustomerCreateUpdateData convert(CreateUpdateCustomerDto source) {
        return new CustomerCreateUpdateData(source.getFirstName(), source.getLastName());
    }
}
