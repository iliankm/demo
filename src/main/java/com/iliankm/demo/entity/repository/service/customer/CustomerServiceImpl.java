package com.iliankm.demo.entity.repository.service.customer;

import com.iliankm.demo.entity.Customer;
import com.iliankm.demo.entity.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

/**
 * Customer service implementation.
 */
@Service
class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Autowired constructor for DI.
     *
     * @param customerRepository {@link CustomerRepository} dependency
     */
    CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
}


