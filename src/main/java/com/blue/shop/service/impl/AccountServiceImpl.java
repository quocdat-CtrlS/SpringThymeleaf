package com.blue.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.blue.shop.entity.Account;
import com.blue.shop.repository.AccountRepository;
import com.blue.shop.service.AccountServices;

@Service
public class AccountServiceImpl implements AccountServices {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Account login(String username, String password) {
		Optional<Account> optExist = findById(username);
		
		if (optExist.isPresent() && bCryptPasswordEncoder.matches(password, optExist.get().getPassword())) {
			
			optExist.get().setPassword("");
			
			return optExist.get();
		}
		
		return null;
	}

	@Override
	public <S extends Account> S save(S entity) {

		Optional<Account> optExist = findById(entity.getUsername());

		if (optExist.isPresent()) {
			if (StringUtils.isEmpty(entity.getPassword())) {
				entity.setPassword(optExist.get().getPassword());
			} else {
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
			}
		} else {
			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		}

		return accountRepo.save(entity);
	}

	@Override
	public <S extends Account> Optional<S> findOne(Example<S> example) {
		return accountRepo.findOne(example);
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountRepo.findAll(pageable);
	}

	@Override
	public List<Account> findAll() {
		return accountRepo.findAll();
	}

	@Override
	public List<Account> findAll(Sort sort) {
		return accountRepo.findAll(sort);
	}

	@Override
	public List<Account> findAllById(Iterable<String> ids) {
		return accountRepo.findAllById(ids);
	}

	@Override
	public Optional<Account> findById(String id) {
		return accountRepo.findById(id);
	}

	@Override
	public <S extends Account> List<S> saveAll(Iterable<S> entities) {
		return accountRepo.saveAll(entities);
	}

	@Override
	public void flush() {
		accountRepo.flush();
	}

	@Override
	public <S extends Account> S saveAndFlush(S entity) {
		return accountRepo.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(String id) {
		return accountRepo.existsById(id);
	}

	@Override
	public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
		return accountRepo.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
		return accountRepo.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Account> entities) {
		accountRepo.deleteInBatch(entities);
	}

	@Override
	public <S extends Account> long count(Example<S> example) {
		return accountRepo.count(example);
	}

	@Override
	public <S extends Account> boolean exists(Example<S> example) {
		return accountRepo.exists(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Account> entities) {
		accountRepo.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return accountRepo.count();
	}

	@Override
	public void deleteById(String id) {
		accountRepo.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<String> ids) {
		accountRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Account entity) {
		accountRepo.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		accountRepo.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		accountRepo.deleteAllInBatch();
	}

	@Override
	public Account getOne(String id) {
		return accountRepo.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Account> entities) {
		accountRepo.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		accountRepo.deleteAll();
	}

	@Override
	public Account getById(String id) {
		return accountRepo.getById(id);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example) {
		return accountRepo.findAll(example);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
		return accountRepo.findAll(example, sort);
	}

}
