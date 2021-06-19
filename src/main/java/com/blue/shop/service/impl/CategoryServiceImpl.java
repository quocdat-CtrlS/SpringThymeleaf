package com.blue.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blue.shop.entity.Category;
import com.blue.shop.repository.CategoryRepository;
import com.blue.shop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	CategoryRepository categoryRepo;

	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	
	
	

	@Override
	public Page<Category> findByNameContaining(String name, Pageable pageable) {
		return categoryRepo.findByNameContaining(name, pageable);
	}




	@Override
	public List<Category> findByNameContaining(String name) {
		return categoryRepo.findByNameContaining(name);
	}



	@Override
	public <S extends Category> S save(S entity) {
		
		return categoryRepo.save(entity);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepo.findAll(pageable);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public List<Category> findAll(Sort sort) {
		return categoryRepo.findAll(sort);
	}

	@Override
	public List<Category> findAllById(Iterable<Long> ids) {
		return categoryRepo.findAllById(ids);
	}

	@Override
	public Optional<Category> findById(Long id) {
		return categoryRepo.findById(id);
	}

	@Override
	public <S extends Category> List<S> saveAll(Iterable<S> entities) {
		return categoryRepo.saveAll(entities);
	}

	@Override
	public void flush() {
		categoryRepo.flush();
	}

	@Override
	public <S extends Category> S saveAndFlush(S entity) {
		return categoryRepo.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return categoryRepo.existsById(id);
	}

	@Override
	public <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities) {
		return categoryRepo.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Category> boolean exists(Example<S> example) {
		return categoryRepo.exists(example);
	}

	@Override
	public long count() {
		return categoryRepo.count();
	}

	@Override
	public void deleteById(Long id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		categoryRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Category entity) {
		categoryRepo.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		categoryRepo.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		categoryRepo.deleteAllInBatch();
	}

	@Override
	public void deleteAll(Iterable<? extends Category> entities) {
		categoryRepo.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		categoryRepo.deleteAll();
	}

	@Override
	public Category getById(Long id) {
		return categoryRepo.getById(id);
	}
	
	
	
}
