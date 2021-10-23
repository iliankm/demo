package com.iliankm.demo.controller;

import com.iliankm.demo.converter.api.ConverterService;
import com.iliankm.demo.dto.CreateUpdateCustomerDto;
import com.iliankm.demo.dto.CustomerDto;
import com.iliankm.demo.service.customer.CustomerCreateUpdateData;
import com.iliankm.demo.service.customer.CustomerService;
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
    private final ConverterService converterService;

    /**
     * Autowired constructor for DI.
     *
     * @param customerService  {@link CustomerService} dependency
     * @param converterService {@link ConverterService} dependency
     */
    CustomerController(CustomerService customerService, ConverterService converterService) {
        this.customerService = customerService;
        this.converterService = converterService;
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
                .map(c -> ResponseEntity.ok().body(converterService.convert(c, CustomerDto.class)))
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
                .stream().map(c -> converterService.convert(c, CustomerDto.class))
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
                customerService.create(
                        converterService.convert(createUpdateCustomerData, CustomerCreateUpdateData.class)).getId(),
                HttpStatus.CREATED);
    }

    /**
     * Updates customer.
     *
     * @param customerId              customer id
     * @param createUpdateCustomerDto customer update data dto
     * @return the id of the updated customer
     */
    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long customerId,
                                         @RequestBody CreateUpdateCustomerDto createUpdateCustomerDto) {
        var customerCreateUpdateData =
                converterService.convert(createUpdateCustomerDto, CustomerCreateUpdateData.class);
        customerService.update(customerId, customerCreateUpdateData);
        return ResponseEntity.ok().body(String.format("Customer %s updated", customerId));
    }
}
