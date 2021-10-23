package com.iliankm.demo.converter;

import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CustomerEntityToCustomerDtoConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerEntityToCustomerDtoConverter.class})
class CustomerEntityToCustomerDtoConverterTest {

    @Autowired
    private CustomerEntityToCustomerDtoConverter converter;

    @Test
    void shouldConvert() {
        // given
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFirstName("John");
        customerEntity.setLastName("Doe");

        // when
        CustomerDto customerDto = converter.convert(customerEntity);

        // then
        assertThat(customerDto.getId(), is(1L));
        assertThat(customerDto.getFirstName(), is("John"));
        assertThat(customerDto.getLastName(), is("Doe"));
    }
}