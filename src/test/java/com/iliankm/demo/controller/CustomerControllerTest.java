package com.iliankm.demo.controller;

import com.iliankm.demo.configuration.ModelMapperConfiguration;
import com.iliankm.demo.converter.api.ConverterService;
import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.service.customer.CustomerCreateUpdateData;
import com.iliankm.demo.service.customer.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.iliankm.demo.controller.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CustomerController}.
 */
@WebMvcTest
@ContextConfiguration(classes = {CustomerController.class})
@Import(ModelMapperConfiguration.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private ConverterService converterService;

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    void shouldGetCustomerById() {
        // given
        var customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        given(customerService.findById(1L)).willReturn(Optional.of(customer));
        given(converterService.convert(customer, CustomerDto.class)).willReturn(CustomerDto.builder().id(1L).build());

        // when & then
        mvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @SneakyThrows
    @Test
    void shouldGetAll() {
        // given
        var customer1 = new CustomerEntity();
        customer1.setId(1L);
        given(customerService.findAll()).willReturn(List.of(customer1));
        given(converterService.convert(customer1, CustomerDto.class)).willReturn(CustomerDto.builder().id(1L).build());

        // when & then
        mvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @SneakyThrows
    @Test
    void shouldCreate() {
        // given
        var customerCreateUpdateData = new CustomerCreateUpdateData("John", "Doe");
        given(converterService.convert(any(CreateUpdateCustomerDto.class), eq(CustomerCreateUpdateData.class))).willReturn(customerCreateUpdateData);
        var customer = new CustomerEntity();
        customer.setId(1L);
        given(customerService.create(customerCreateUpdateData)).willReturn(customer);
        var createUpdateCustomerDto = new CreateUpdateCustomerDto();
        createUpdateCustomerDto.setFirstName("John");
        createUpdateCustomerDto.setLastName("Doe");

        // when & then
        mvc.perform(post("/api/v1/customers")
                .content(asJsonString(createUpdateCustomerDto)).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @SneakyThrows
    @Test
    void shouldUpdate() {
        // given
        var customerCreateUpdateData = new CustomerCreateUpdateData("John", "Doe");
        given(converterService.convert(any(CreateUpdateCustomerDto.class), eq(CustomerCreateUpdateData.class))).willReturn(customerCreateUpdateData);
        var customer = new CustomerEntity();
        customer.setId(1L);
        given(customerService.update(1L, customerCreateUpdateData)).willReturn(customer);
        var createUpdateCustomerDto = new CreateUpdateCustomerDto();
        createUpdateCustomerDto.setFirstName("John");
        createUpdateCustomerDto.setLastName("Doe");

        // when & then
        mvc.perform(put("/api/v1/customers/1")
                        .content(asJsonString(createUpdateCustomerDto)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer 1 updated"));
    }
}