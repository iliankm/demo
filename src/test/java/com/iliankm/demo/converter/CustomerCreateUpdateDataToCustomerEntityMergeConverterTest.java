package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.MergeConverter;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.service.customer.CustomerCreateUpdateData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CustomerCreateUpdateDataToCustomerEntityMergeConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerCreateUpdateDataToCustomerEntityMergeConverter.class)
class CustomerCreateUpdateDataToCustomerEntityMergeConverterTest {

    @Autowired
    private MergeConverter<CustomerCreateUpdateData, CustomerEntity> converter;

    @Test
    void shouldConvert() {
        // given
        var customerCreateUpdateData = new CustomerCreateUpdateData("John", "Doe");
        var customerEntity = new CustomerEntity();

        // when
        var result = converter.convert(customerCreateUpdateData, customerEntity);

        // then
        assertThat(result, is(customerEntity));
        assertThat(customerEntity.getFirstName(), is("John"));
        assertThat(customerEntity.getLastName(), is("Doe"));
    }
}