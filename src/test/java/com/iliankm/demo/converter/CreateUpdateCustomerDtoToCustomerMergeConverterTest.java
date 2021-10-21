package com.iliankm.demo.converter;

import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import com.iliankm.demo.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CreateUpdateCustomerDtoToCustomerMergeConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CreateUpdateCustomerDtoToCustomerMergeConverter.class)
class CreateUpdateCustomerDtoToCustomerMergeConverterTest {

    @Autowired
    private CreateUpdateCustomerDtoToCustomerMergeConverter mergeConverter;

    @Test
    void shouldConvert() {
        // given
        final var dto = new CreateUpdateCustomerDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        final var customer = new Customer();

        // when
        mergeConverter.convert(dto, customer);

        // then
        assertThat(customer.getFirstName(), is("John"));
        assertThat(customer.getLastName(), is("Doe"));
    }
}