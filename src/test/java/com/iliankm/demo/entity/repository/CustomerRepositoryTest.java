package com.iliankm.demo.entity.repository;

import com.iliankm.demo.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.stream.Stream;

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
    void shouldSave() {
        // given
        Customer customer = customer("John", "Doe");

        // when
        Long id = customerRepository.saveAndFlush(customer).getId();

        // then
        assertThat(customerRepository.findById(id).isPresent(), is(true));
    }

    @ParameterizedTest
    @MethodSource("shouldFailValidationTestCases")
    void shouldFailValidation(Customer customer) {
        // given & when & then
        assertThrows(ConstraintViolationException.class, () -> customerRepository.saveAndFlush(customer));
    }

    private static Stream<Arguments> shouldFailValidationTestCases() {
        final char[] longNameArr = new char[101];
        Arrays.fill(longNameArr, 'A');
        final String longName = new String(longNameArr);
        return Stream.of(
                Arguments.of(new Customer()),
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

    private static Customer customer(String firstName, String lastName) {
        final Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

}