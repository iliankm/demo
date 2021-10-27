package com.iliankm.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO for customer update data.
 */
@Getter
@Setter
public class CustomerUpdateDto {

    private JsonNullable<String> firstName = JsonNullable.undefined();

    private JsonNullable<String> lastName = JsonNullable.undefined();
}
