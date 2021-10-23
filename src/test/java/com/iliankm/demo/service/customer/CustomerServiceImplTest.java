package com.iliankm.demo.service.customer;

import com.iliankm.demo.converter.api.ConverterService;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.exception.NotFoundException;
import com.iliankm.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;

import static com.iliankm.demo.util.AnnotationUtil.forClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

/**
 * Test for {@link CustomerServiceImpl}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class})
class CustomerServiceImplTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private ConverterService converterService;

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldFindById() {
        // given
        CustomerEntity customerEntity = customer(1L, "John", "D");
        given(customerRepository.findById(1L)).willReturn(Optional.of(customerEntity));

        // when
        Optional<Customer> found = customerService.findById(1L);

        // then
        assertThat(found.orElseThrow(), is(customerEntity));
    }

    @Test
    void shouldFindAll() {
        // given
        CustomerEntity customerEntity = customer(2L, "J", "Doe");
        given(customerRepository.findAll()).willReturn(List.of(customerEntity));

        // when
        List<Customer> found = customerService.findAll();

        // then
        assertThat(found, contains(customerEntity));
    }

    @Test
    void shouldCreate() {
        // given
        CustomerEntity created = customer(3L, "John", "Doe");
        given(customerRepository.save(any(CustomerEntity.class))).willReturn(created);
        var createUpdateData = new CustomerCreateUpdateData("John", "Doe");
        given(converterService.convert(createUpdateData, created)).willReturn(created);

        // when
        Customer result = customerService.create(createUpdateData);

        // then
        assertThat(result, is(created));
    }

    @Test
    void shouldUpdate() {
        // given
        var customerEntity = new CustomerEntity();
        given(customerRepository.findById(1L)).willReturn(Optional.of(customerEntity));
        var createUpdateData = new CustomerCreateUpdateData("John", "Doe");
        given(converterService.convert(createUpdateData, customerEntity)).willReturn(customerEntity);
        given(customerRepository.save(customerEntity)).willReturn(customerEntity);

        // when
        var result = customerService.update(1L, createUpdateData);

        // then
        assertThat(result, is(customerEntity));
    }

    @Test
    void shouldUpdateThrowNotFoundException() {
        // given
        given(customerRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> customerService.update(1L, new CustomerCreateUpdateData("John", "Doe")));
    }

    @Test
    void theClassShouldHaveServiceAnnotation() {
        // given & when
        Service service = forClass(CustomerServiceImpl.class)
                .annotation(Service.class);

        // then
        assertThat(service, notNullValue());
    }

    @Test
    void createMethodShouldHaveTransactionalAnnotation() {
        // given & when
        Transactional transactional = forClass(CustomerServiceImpl.class)
                .method("create", CustomerCreateUpdateData.class)
                .annotation(Transactional.class);

        // then
        assertThat(transactional, notNullValue());
    }

    @Test
    void createMethodShouldHaveValidAnnotationOnTheArgument() {
        // given & when
        Valid valid = forClass(CustomerService.class)
                .method("create", CustomerCreateUpdateData.class)
                .parameter(0)
                .annotation(Valid.class);

        // then
        assertThat(valid, notNullValue());
    }

    private static CustomerEntity customer(Long id, String firstName, String lastName) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(id);
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        return customerEntity;
    }

}