package com.iliankm.demo.dto;

import lombok.Data;

/**
 * Create / update data for customer resource.
 */
@Data
public class CustomerCreateDto {
    private String firstName;
    private String lastName;
}