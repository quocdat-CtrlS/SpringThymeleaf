package com.blue.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.shop.entity.Category;
import com.blue.shop.entity.Customer;

@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByNameContaining(String name);
	Page<Customer> findByNameContaining(String name, Pageable pageable);
}
