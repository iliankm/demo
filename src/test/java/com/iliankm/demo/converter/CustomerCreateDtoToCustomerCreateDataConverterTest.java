package com.iliankm.demo.converter;

import com.iliankm.demo.dto.CustomerCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CustomerCreateDtoToCustomerCreateDataConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerCreateDtoToCustomerCreateDataConverter.class)
class CustomerCreateDtoToCustomerCreateDataConverterTest {

    @Autowired
    private CustomerCreateDtoToCustomerCreateDataConverter converter;

    @Test
    void shouldConvert() {
        // given
        var dto = new CustomerCreateDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");

        // when
        var createData = converter.convert(dto);

        // then
        assertThat(createData.getFirstName(), is("John"));
        assertThat(createData.getLastName(), is("Doe"));
    }

}