package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.MergeConverter;
import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import com.iliankm.demo.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * Merge converter from {@link CreateUpdateCustomerDto} to {@link Customer}.
 */
@Component
class CreateUpdateCustomerDtoToCustomerMergeConverter implements MergeConverter<CreateUpdateCustomerDto, Customer> {

    @Override
    public Customer convert(CreateUpdateCustomerDto source, Customer destination) {
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        return destination;
    }
}
