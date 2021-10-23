package com.iliankm.demo.controller;

import com.iliankm.demo.converter.api.ConverterService;
import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.exception.NotFoundException;
import com.iliankm.demo.service.customer.CustomerCreateUpdateData;
import com.iliankm.demo.service.customer.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;

import static com.iliankm.demo.controller.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link ResponseExceptionAdvice}.
 */
@WebMvcTest
@ContextConfiguration(classes = {CustomerController.class, ResponseExceptionAdvice.class})
class ResponseExceptionAdviceTest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private ConverterService converterService;

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    void shouldMapValidationException() {
        // given
        given(customerService.create(any(CustomerCreateUpdateData.class))).willThrow(ValidationException.class);
        given(converterService.convert(any(CreateUpdateCustomerDto.class), eq(CustomerCreateUpdateData.class))).willReturn(new CustomerCreateUpdateData("", ""));
        var customerDto = CustomerDto.builder().id(1L);

        // when & then
        mvc.perform(post("/api/v1/customers")
                        .content(asJsonString(customerDto)).contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void shouldMapNotFoundException() {
        // given
        given(converterService.convert(any(CreateUpdateCustomerDto.class), eq(CustomerCreateUpdateData.class))).willReturn(new CustomerCreateUpdateData("", ""));
        given(customerService.update(eq(1L), any(CustomerCreateUpdateData.class))).willThrow(NotFoundException.class);
        var updateDataDto = new CreateUpdateCustomerDto();

        // when & then
        mvc.perform(put("/api/v1/customers/1")
                        .content(asJsonString(updateDataDto)).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}