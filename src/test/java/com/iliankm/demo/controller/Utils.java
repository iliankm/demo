package com.iliankm.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iliankm.demo.configuration.ObjectMapperConfiguration;

/**
 * Utilities for controller tests.
 */
final class Utils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapperConfiguration()
            .objectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    private Utils() {
    }

    /**
     * Converts given object to json string.
     *
     * @param obj the object
     * @return json string representing the object
     */
    static String asJsonString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
