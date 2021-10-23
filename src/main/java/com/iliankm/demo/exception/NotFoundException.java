package com.iliankm.demo.exception;

/**
 * NotFoundException is thrown by the service layer when certain object was not found.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs NotFoundException with null as its detail message.
     */
    public NotFoundException() {
        super();
    }

    /**
     * Constructs NotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
