package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.MergeConverter;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.service.customer.CustomerCreateData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CustomerCreateDataToCustomerEntityMergeConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerCreateDataToCustomerEntityMergeConverter.class)
class CustomerCreateDataToCustomerEntityMergeConverterTest {

    @Autowired
    private MergeConverter<CustomerCreateData, CustomerEntity> converter;

    @Test
    void shouldConvert() {
        // given
        var customerCreateData = new CustomerCreateData("John", "Doe");
        var customerEntity = new CustomerEntity();

        // when
        var result = converter.convert(customerCreateData, customerEntity);

        // then
        assertThat(result, is(customerEntity));
        assertThat(customerEntity.getFirstName(), is("John"));
        assertThat(customerEntity.getLastName(), is("Doe"));
    }
}