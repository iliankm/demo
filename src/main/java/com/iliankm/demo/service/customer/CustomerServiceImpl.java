package com.iliankm.demo.service.customer;

import com.iliankm.demo.converter.api.ConverterService;
import com.iliankm.demo.entity.CustomerEntity;
import com.iliankm.demo.exception.NotFoundException;
import com.iliankm.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import static java.util.function.Function.identity;

/**
 * Customer service implementation.
 */
@Service
@Validated
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ConverterService converterService;

    /**
     * Autowired constructor for DI.
     *
     * @param customerRepository {@link CustomerRepository} dependency
     * @param converterService {@link ConverterService} dependency
     */
    CustomerServiceImpl(CustomerRepository customerRepository, ConverterService converterService) {
        this.customerRepository = customerRepository;
        this.converterService = converterService;
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId).map(identity());
    }

    @Override
    public List<Customer> findAll() {
        return Collections.unmodifiableList(customerRepository.findAll());
    }

    @Override
    @Transactional
    public Customer create(CustomerCreateUpdateData createData) {
        var customerEntity = new CustomerEntity();
        converterService.convert(createData, customerEntity);
        return customerRepository.save(customerEntity);
    }

    @Override
    @Transactional
    public Customer update(Long id, CustomerCreateUpdateData updateData) {
        var customerEntity = customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer with id: " + id + " was not found."));
        converterService.convert(updateData, customerEntity);
        return customerRepository.save(customerEntity);
    }
}