package com.iliankm.demo.entity.repository.service.customer;

import com.iliankm.demo.entity.Customer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Customer service interface.
 */
public interface CustomerService {
    /**
     * Finds customer by its id.
     *
     * @param customerId customer id
     * @return the customer or Optional#empty() if not found
     */
    Optional<Customer> findById(@NotNull Long customerId);

    /**
     * Finds all customers.
     *
     * @return all customers or empty list
     */
    List<Customer> findAll();

    /**
     * Saves new customer.
     *
     * @param customer customer to save
     * @return saved customer
     */
    Customer save(@Valid Customer customer);
}
