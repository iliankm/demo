package com.iliankm.demo.controller;

import com.iliankm.demo.converter.api.ConverterService;
import com.iliankm.demo.dto.CustomerCreateDto;
import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.dto.CustomerUpdateDto;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.exception.NotFoundException;
import com.iliankm.demo.service.customer.Customer;
import com.iliankm.demo.service.customer.CustomerCreateData;
import com.iliankm.demo.service.customer.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
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
        given(customerService.create(any(CustomerCreateData.class))).willThrow(ValidationException.class);
        given(converterService.convert(any(CustomerCreateDto.class), eq(CustomerCreateData.class))).willReturn(new CustomerCreateData("", ""));
        var customerDto = new CustomerDto();
        customerDto.setId(1L);

        // when & then
        mvc.perform(post("/api/v1/customers")
                        .content(asJsonString(customerDto)).contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void shouldMapNotFoundException() {
        // given
        Customer customer = new CustomerEntity();
        given(customerService.findById(1L)).willReturn(Optional.of(customer));
        given(converterService.convert(any(CustomerUpdateDto.class), eq(customer))).willReturn(customer);
        given(customerService.update(customer)).willThrow(NotFoundException.class);

        // when & then
        mvc.perform(put("/api/v1/customers/1")
                        .content(asJsonString(new CustomerCreateDto())).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}