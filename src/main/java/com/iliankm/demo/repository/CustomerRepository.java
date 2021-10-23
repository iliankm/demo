package com.iliankm.demo.repository;

import com.iliankm.demo.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link CustomerEntity}.
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
