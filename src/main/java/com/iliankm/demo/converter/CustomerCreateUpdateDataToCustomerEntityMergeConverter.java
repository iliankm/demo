package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.MergeConverter;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.service.customer.CustomerCreateUpdateData;
import org.springframework.stereotype.Component;

/**
 * Merge converter from {@link CustomerCreateUpdateData} to {@link CustomerEntity}.
 */
@Component
class CustomerCreateUpdateDataToCustomerEntityMergeConverter
        implements MergeConverter<CustomerCreateUpdateData, CustomerEntity> {

    @Override
    public CustomerEntity convert(CustomerCreateUpdateData source, CustomerEntity destination) {
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        return destination;
    }
}
