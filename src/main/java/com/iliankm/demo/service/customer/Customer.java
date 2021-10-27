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
     * Sets the first name.
     *
     * @param firstName the first name to set
     */
    void setFirstName(String firstName);

    /**
     * Returns customer's last name.
     *
     * @return customer's last name
     */
    String getLastName();

    /**
     * Sets the last name.
     *
     * @param lastName the last name to set
     */
    void setLastName(String lastName);

}
