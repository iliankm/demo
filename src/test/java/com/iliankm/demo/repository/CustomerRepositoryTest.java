package com.iliankm.demo.repository;

import com.iliankm.demo.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.stream.Stream;
import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test for {@link CustomerRepository}.
 */
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Sql("/create-customer.sql")
    void shouldFindById() {
        // given & when
        var customer = customerRepository.findById(1L);

        // then
        assertThat(customer.isPresent(), is(true));
        assertThat(customer.get().getId(), is(1L));
    }

    @Test
    void shouldSave() {
        // given
        var customer = customer("John", "Doe");

        // when
        Long id = customerRepository.saveAndFlush(customer).getId();

        // then
        assertThat(customerRepository.findById(id).isPresent(), is(true));
    }

    @ParameterizedTest
    @MethodSource("shouldFailValidationTestCases")
    void shouldFailValidation(CustomerEntity customerEntity) {
        // given & when & then
        assertThrows(ConstraintViolationException.class, () -> customerRepository.saveAndFlush(customerEntity));
    }

    private static Stream<Arguments> shouldFailValidationTestCases() {
        final var longNameArr = new char[101];
        Arrays.fill(longNameArr, 'A');
        final var longName = new String(longNameArr);
        return Stream.of(
                Arguments.of(new CustomerEntity()),
                Arguments.of(customer("", "Doe")),
                Arguments.of(customer(" ", "Doe")),
                Arguments.of(customer(null, "Doe")),
                Arguments.of(customer(longName, "Doe")),
                Arguments.of(customer("John", "")),
                Arguments.of(customer("John", " ")),
                Arguments.of(customer("John", null)),
                Arguments.of(customer("John", longName))
        );
    }

    private static CustomerEntity customer(String firstName, String lastName) {
        final var customer = new CustomerEntity();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

}