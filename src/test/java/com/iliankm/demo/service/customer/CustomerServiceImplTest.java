package com.iliankm.demo.service.customer;

import com.iliankm.demo.entity.Customer;
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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;

/**
 * Test for {@link CustomerServiceImpl}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class})
class CustomerServiceImplTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldFindById() {
        // given
        Customer customer = customer(1L, "John", "D");
        given(customerRepository.findById(1L)).willReturn(Optional.of(customer));

        // when
        Optional<Customer> found = customerService.findById(1L);

        // then
        assertThat(found.orElseThrow(), is(customer));
    }

    @Test
    void shouldFindAll() {
        // given
        Customer customer = customer(1L, "J", "Doe");
        given(customerRepository.findAll()).willReturn(List.of(customer));

        // when
        List<Customer> found = customerService.findAll();

        // then
        assertThat(found, contains(customer));
    }

    @Test
    void shouldSave() {
        // given
        Customer customer = customer(null, "John", "Doe");
        Customer created = customer(1L, "John", "Doe");
        given(customerRepository.save(customer)).willReturn(created);

        // when
        Customer result = customerService.save(customer);

        // then
        assertThat(result, is(created));
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
    void saveMethodShouldHaveTransactionalAnnotation() {
        // given & when
        Transactional transactional = forClass(CustomerServiceImpl.class)
                .method("save", Customer.class)
                .annotation(Transactional.class);

        // then
        assertThat(transactional, notNullValue());
    }

    @Test
    void saveMethodShouldHaveValidAnnotationOnTheArgument() {
        // given & when
        Valid valid = forClass(CustomerService.class)
                .method("save", Customer.class)
                .parameter(0)
                .annotation(Valid.class);

        // then
        assertThat(valid, notNullValue());
    }

    private static Customer customer(Long id, String firstName, String lastName) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

}