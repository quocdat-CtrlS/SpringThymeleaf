package com.blue.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.blue.shop.entity.Customer;

public interface UserService {

	<S extends Customer> List<S> findAll(Example<S> example, Sort sort);

	<S extends Customer> List<S> findAll(Example<S> example);

	Customer getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Customer> entities);

	Customer getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Customer entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	long count();

	void deleteAllInBatch(Iterable<Customer> entities);

	<S extends Customer> boolean exists(Example<S> example);

	<S extends Customer> long count(Example<S> example);

	void deleteInBatch(Iterable<Customer> entities);

	<S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Customer> S saveAndFlush(S entity);

	void flush();

	<S extends Customer> List<S> saveAll(Iterable<S> entities);

	Optional<Customer> findById(Long id);

	List<Customer> findAllById(Iterable<Long> ids);

	List<Customer> findAll(Sort sort);

	List<Customer> findAll();

	Page<Customer> findAll(Pageable pageable);

	<S extends Customer> Optional<S> findOne(Example<S> example);

	<S extends Customer> S save(S entity);

	List<Customer> findByNameContaining(String name);
	Page<Customer> findByNameContaining(String name, Pageable pageable);


}
