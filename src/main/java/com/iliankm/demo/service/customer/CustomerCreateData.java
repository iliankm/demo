package com.iliankm.demo.service.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Create customer data object.
 */
@RequiredArgsConstructor
@Getter
public class CustomerCreateData {

    @NotBlank
    @Size(max = 100)
    private final String firstName;

    @NotBlank
    @Size(max = 100)
    private final String lastName;
}
