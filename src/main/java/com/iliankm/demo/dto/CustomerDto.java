package com.iliankm.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for customer resource.
 */
@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;

}