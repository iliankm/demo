package com.iliankm.demo.service.customer;

import com.sun.istack.NotNull;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

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
     * Creates new customer.
     *
     * @param createData customer create data
     * @return created customer
     */
    Customer create(@Valid CustomerCreateData createData);

    /**
     * Updates a customer.
     *
     * @param customer the customer to update
     * @return updated customer
     */
    Customer update(@Valid Customer customer);

}
