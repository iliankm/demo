package com.iliankm.demo.service.customer;

/**
 * Customer domain model.
 */
public interface Customer {

    /**
     * Returns customer's id.
     *
     * @return customer's id
     */
    Long getId();

    /**
     * Returns customer's first name.
     *
     * @return customer's first name
     */
    String getFirstName();

    /**
     * Returns customer's last name.
     *
     * @return customer's last name
     */
    String getLastName();
}
