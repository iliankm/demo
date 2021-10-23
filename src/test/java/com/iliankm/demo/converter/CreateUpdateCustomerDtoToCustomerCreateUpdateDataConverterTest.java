package com.iliankm.demo.converter;

import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test for {@link CreateUpdateCustomerDtoToCustomerCreateUpdateDataConverter}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CreateUpdateCustomerDtoToCustomerCreateUpdateDataConverter.class)
class CreateUpdateCustomerDtoToCustomerCreateUpdateDataConverterTest {

    @Autowired
    private CreateUpdateCustomerDtoToCustomerCreateUpdateDataConverter converter;

    @Test
    void shouldConvert() {
        // given
        var dto = new CreateUpdateCustomerDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");

        // when
        var createUpdateData = converter.convert(dto);

        // then
        assertThat(createUpdateData.getFirstName(), is("John"));
        assertThat(createUpdateData.getLastName(), is("Doe"));
    }

}