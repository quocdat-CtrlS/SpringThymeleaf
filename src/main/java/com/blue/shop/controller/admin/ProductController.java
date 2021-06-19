package com.blue.shop.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blue.shop.entity.Category;
import com.blue.shop.entity.Product;
import com.blue.shop.model.CategoryDTO;
import com.blue.shop.model.ProductDTO;
import com.blue.shop.service.CategoryService;
import com.blue.shop.service.ProductService;
import com.blue.shop.service.StorageService;

@Controller
@RequestMapping("admin/products")
public class ProductController {

	@Autowired
	CategoryService cateServices;

	@Autowired
	ProductService productServices;

	@Autowired
	StorageService storageService;

	@ModelAttribute("categories")
	public List<CategoryDTO> getCategories() {
		return cateServices.findAll().stream().map(item -> {
			CategoryDTO dto = new CategoryDTO();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@GetMapping("create")
	public String add(Model model) {
		ProductDTO dto = new ProductDTO();
		dto.setIsEdit(false);

		model.addAttribute("product", dto);

		return "admin/products/create";
	}

	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {

		Optional<Product> opt = productServices.findById(productId);
		ProductDTO dto = new ProductDTO();

		if (opt.isPresent()) {
			Product entity = opt.get();

			BeanUtils.copyProperties(entity, dto);

			dto.setCategoryId(entity.getCategory().getCategoryId());
			dto.setIsEdit(true);

			model.addAttribute("product", dto);

			return new ModelAndView("admin/products/create", model);
		}

		model.addAttribute("message", "Product is not exsited");

		return new ModelAndView("forward:/admin/products", model);
	}

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAndResources(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model, @PathVariable("productId") Long productId) throws IOException {

		Optional<Product> opt = productServices.findById(productId);

		if (opt.isPresent()) {
			if (!StringUtils.isEmpty(opt.get().getImage())) {
				storageService.delete(opt.get().getImage());
			}

			productServices.delete(opt.get());

			model.addAttribute("message", "Deleted Success!");
		} else {
			model.addAttribute("message", "Deleted Failed!");
		}

		return new ModelAndView("forward:/admin/products", model);
	}

	@PostMapping("update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("product") ProductDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/products/create");
		}

		Product entity = new Product();
		BeanUtils.copyProperties(dto, entity);

		Category category = new Category();

		category.setCategoryId(dto.getCategoryId());
		entity.setCategory(category);

		if (dto.getImageFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();

			String uuString = uuid.toString();

			entity.setImage(storageService.getStoredFilename(dto.getImageFile(), uuString));

			storageService.store(dto.getImageFile(), entity.getImage());
		}

		productServices.save(entity);

		model.addAttribute("message", "Product is saved!");

		return new ModelAndView("forward:/admin/products", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Product> list = productServices.findAll();

		model.addAttribute("products", list);

		return "admin/products/list";
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {

		List<Category> list = null;

		if (StringUtils.hasText(name)) {
			list = cateServices.findByNameContaining(name);
		} else {
			list = cateServices.findAll();
		}

		model.addAttribute("products", list);

		return "admin/products/search";
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
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);

			if (totalPages > 5) {
				if (end == totalPages)
					start = end - 5;
				else if (start == 1)
					end = start + 5;
			}

			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("categoryPage", resultPage);

		return "admin/products/searchpaginated";
	}

}
