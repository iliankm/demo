package com.iliankm.demo.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO for customer resource.
 */
@Getter
@Builder
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;

}