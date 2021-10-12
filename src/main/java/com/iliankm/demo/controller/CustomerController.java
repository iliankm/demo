package com.iliankm.demo.controller;

import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.entity.Customer;
import com.iliankm.demo.entity.repository.service.customer.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Customer resource REST controller.
 */
@RestController
@RequestMapping("api/v1/customers")
class CustomerController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    /**
     * Autowired constructor for DI.
     *
     * @param customerService {@link CustomerService} dependency
     * @param modelMapper     {@link ModelMapper} dependency
     */
    CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    /**
     * Gets customer resource by id.
     *
     * @param customerId customer id
     * @return customer resource
     */
    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable("id") Long customerId) {
        return customerService.findById(customerId)
                .map(c -> ResponseEntity.ok().body(modelMapper.map(c, CustomerDto.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Gets all customers.
     *
     * @return customer resources
     */
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok().body(customerService.findAll()
                .stream().map(c -> modelMapper.map(c, CustomerDto.class))
                .collect(Collectors.toList()));
    }

    /**
     * Creates customer resource.
     *
     * @param createUpdateCustomerData customer resource creation data
     * @return the id of the created customer
     */
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CreateUpdateCustomerDto createUpdateCustomerData) {
        return new ResponseEntity<>(
                customerService.save(modelMapper.map(createUpdateCustomerData, Customer.class)).getId(),
                HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long customerId,
                                         @RequestBody CreateUpdateCustomerDto createUpdateCustomerData) {
        return customerService.findById(customerId)
                .map(c -> {
                    modelMapper.map(createUpdateCustomerData, c);
                    return c;
                })
                .map(customerService::save)
                .map(c -> ResponseEntity.ok().body(String.format("Customer %s updated", customerId)))
                .orElse(ResponseEntity.notFound().build());
    }
}
