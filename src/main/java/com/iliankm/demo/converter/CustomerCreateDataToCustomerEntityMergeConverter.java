package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.MergeConverter;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.service.customer.CustomerCreateData;
import org.springframework.stereotype.Component;

/**
 * Merge converter from {@link CustomerCreateData} to {@link CustomerEntity}.
 */
@Component
class CustomerCreateDataToCustomerEntityMergeConverter implements MergeConverter<CustomerCreateData, CustomerEntity> {

    @Override
    public CustomerEntity convert(CustomerCreateData source, CustomerEntity destination) {
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        return destination;
    }
}
