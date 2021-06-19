package com.blue.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blue.shop.entity.Customer;
import com.blue.shop.repository.UserRepository;
import com.blue.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	

	@Override
	public <S extends Customer> S save(S entity) {
		return userRepo.save(entity);
	}

	@Override
	public <S extends Customer> Optional<S> findOne(Example<S> example) {
		return userRepo.findOne(example);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return userRepo.findAll(pageable);
	}

	@Override
	public List<Customer> findAll() {
		return userRepo.findAll();
	}

	@Override
	public List<Customer> findAll(Sort sort) {
		return userRepo.findAll(sort);
	}

	@Override
	public List<Customer> findAllById(Iterable<Long> ids) {
		return userRepo.findAllById(ids);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return userRepo.findById(id);
	}

	@Override
	public <S extends Customer> List<S> saveAll(Iterable<S> entities) {
		return userRepo.saveAll(entities);
	}

	@Override
	public void flush() {
		userRepo.flush();
	}

	@Override
	public <S extends Customer> S saveAndFlush(S entity) {
		return userRepo.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return userRepo.existsById(id);
	}

	@Override
	public <S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities) {
		return userRepo.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable) {
		return userRepo.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Customer> entities) {
		userRepo.deleteInBatch(entities);
	}

	@Override
	public <S extends Customer> long count(Example<S> example) {
		return userRepo.count(example);
	}

	@Override
	public <S extends Customer> boolean exists(Example<S> example) {
		return userRepo.exists(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Customer> entities) {
		userRepo.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return userRepo.count();
	}

	@Override
	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		userRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Customer entity) {
		userRepo.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		userRepo.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		userRepo.deleteAllInBatch();
	}

	@Override
	public Customer getOne(Long id) {
		return userRepo.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		userRepo.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		userRepo.deleteAll();
	}

	@Override
	public Customer getById(Long id) {
		return userRepo.getById(id);
	}

	@Override
	public <S extends Customer> List<S> findAll(Example<S> example) {
		return userRepo.findAll(example);
	}

	@Override
	public <S extends Customer> List<S> findAll(Example<S> example, Sort sort) {
		return userRepo.findAll(example, sort);
	}

	@Override
	public List<Customer> findByNameContaining(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Customer> findByNameContaining(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
