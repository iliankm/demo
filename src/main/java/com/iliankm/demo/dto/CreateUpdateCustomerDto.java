package com.iliankm.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Create / update data for customer resource.
 */
@Getter
@Setter
public class CreateUpdateCustomerDto {
    private String firstName;
    private String lastName;
}