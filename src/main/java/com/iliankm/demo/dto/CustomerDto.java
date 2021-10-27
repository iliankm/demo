package com.iliankm.demo.dto;

import lombok.Data;

/**
 * DTO for customer resource.
 */
@Data
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
}