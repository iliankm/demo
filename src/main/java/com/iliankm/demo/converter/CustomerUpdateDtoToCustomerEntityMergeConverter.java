package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.MergeConverter;
import com.iliankm.demo.dto.CustomerUpdateDto;
import com.iliankm.demo.entity.CustomerEntity;
import org.springframework.stereotype.Component;

/**
 * Merge converter from {@link CustomerUpdateDto} to {@link CustomerEntity}.
 */
@Component
class CustomerUpdateDtoToCustomerEntityMergeConverter implements MergeConverter<CustomerUpdateDto, CustomerEntity> {

    @Override
    public CustomerEntity convert(CustomerUpdateDto source, CustomerEntity destination) {

        if (source.getFirstName().isPresent()) {
            destination.setFirstName(source.getFirstName().get());
        }
        if (source.getLastName().isPresent()) {
            destination.setLastName(source.getLastName().get());
        }

        return destination;
    }
}
