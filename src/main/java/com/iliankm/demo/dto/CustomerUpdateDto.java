package com.iliankm.demo.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO for customer update data.
 */
@Data
public class CustomerUpdateDto {
    private JsonNullable<String> firstName = JsonNullable.undefined();
    private JsonNullable<String> lastName = JsonNullable.undefined();
}
