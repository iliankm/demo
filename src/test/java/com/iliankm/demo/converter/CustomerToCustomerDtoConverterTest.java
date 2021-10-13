package com.iliankm.demo.converter;

import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CustomerToCustomerDtoConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerToCustomerDtoConverter.class})
class CustomerToCustomerDtoConverterTest {

    @Autowired
    private CustomerToCustomerDtoConverter converter;

    @Test
    void shouldConvert() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        // when
        CustomerDto customerDto = converter.convert(customer);

        // then
        assertThat(customerDto.getId(), is(1L));
        assertThat(customerDto.getFirstName(), is("John"));
        assertThat(customerDto.getLastName(), is("Doe"));
    }
}