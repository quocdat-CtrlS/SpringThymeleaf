package com.blue.shop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.blue.shop.entity.Category;
import com.blue.shop.model.CategoryDTO;
import com.blue.shop.service.CategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

	@Autowired
	CategoryService cateServices;

	@GetMapping("create")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDTO());
		return "admin/categories/create";
	}

	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {

		Optional<Category> opt = cateServices.findById(categoryId);
		CategoryDTO dto = new CategoryDTO();

		if (opt.isPresent()) {
			Category entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);

			model.addAttribute("category", dto);

			return new ModelAndView("admin/categories/create", model);
		}

		model.addAttribute("message", "Category is not exsited");

		return new ModelAndView("admin/categories/create", model);
	}

	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {

		cateServices.deleteById(categoryId);

		model.addAttribute("message", "Deleted Success!");

		return new ModelAndView("forward:/admin/categories/search", model);
	}

	@PostMapping("update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("category") CategoryDTO dto,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/create");
		}

		Category entity = new Category();
		BeanUtils.copyProperties(dto, entity);

		cateServices.save(entity);

		model.addAttribute("message", "Category is saved!");

		return new ModelAndView("forward:/admin/categories", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Category> list = cateServices.findAll();

		model.addAttribute("categories", list);

		return "admin/categories/list";
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {

		List<Category> list = null;

		if (StringUtils.hasText(name)) {
			list = cateServices.findByNameContaining(name);
		} else {
			list = cateServices.findAll();
		}

		model.addAttribute("categories", list);

		return "admin/categories/search";
	}

	@GetMapping("searchpaginated")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
		
		Page<Category> resultPage = null;

		if (StringUtils.hasText(name)) {
			resultPage = cateServices.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = cateServices.findAll(pageable);
		}
		
		int totalPages = resultPage.getTotalPages();
		
		if (totalPages > 0) {
			int start = Math.max(1, currentPage-2);
			int end = Math.min(currentPage + 2, totalPages);
			
			if (totalPages > 5) {
				if (end == totalPages) start = end - 5;
				else if (start == 1) end = start + 5;
			}
			
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
					.boxed()
					.collect(Collectors.toList());
			
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("categoryPage", resultPage);

		return "admin/categories/searchpaginated";
	}

}
