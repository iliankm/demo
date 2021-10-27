package com.iliankm.demo.converter;

import com.iliankm.demo.converter.api.MergeConverter;
import com.iliankm.demo.dto.CustomerUpdateDto;
import com.iliankm.demo.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CustomerUpdateDtoToCustomerEntityMergeConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerUpdateDtoToCustomerEntityMergeConverter.class)
class CustomerUpdateDtoToCustomerEntityMergeConverterTest {

    @Autowired
    private MergeConverter<CustomerUpdateDto, CustomerEntity> mergeConverter;

    @Test
    void shouldMerge() {
        // given
        var customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setFirstName(JsonNullable.of("John"));
        customerUpdateDto.setLastName(JsonNullable.of("Doe"));
        var customerEntity = new CustomerEntity();

        // when
        mergeConverter.convert(customerUpdateDto, customerEntity);

        // then
        assertThat(customerEntity.getFirstName(), is("John"));
        assertThat(customerEntity.getLastName(), is("Doe"));
    }

    @Test
    void shouldNotMerge() {
        // given
        var customerUpdateDto = new CustomerUpdateDto();
        var customerEntity = new CustomerEntity();
        customerEntity.setFirstName("Max");
        customerEntity.setLastName("Cavalera");

        // when
        mergeConverter.convert(customerUpdateDto, customerEntity);

        // then
        assertThat(customerEntity.getFirstName(), is("Max"));
        assertThat(customerEntity.getLastName(), is("Cavalera"));
    }
}