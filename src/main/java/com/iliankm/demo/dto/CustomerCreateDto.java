package com.iliankm.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * Create / update data for customer resource.
 */
@Getter
@Setter
public class CustomerCreateDto {
    private String firstName;
    private String lastName;
}