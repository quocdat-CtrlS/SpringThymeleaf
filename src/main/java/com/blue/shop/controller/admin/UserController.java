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
import com.blue.shop.entity.Customer;
import com.blue.shop.entity.Product;
import com.blue.shop.model.CategoryDTO;
import com.blue.shop.model.CustomerDTO;
import com.blue.shop.model.ProductDTO;
import com.blue.shop.service.CategoryService;
import com.blue.shop.service.ProductService;
import com.blue.shop.service.StorageService;
import com.blue.shop.service.UserService;

@Controller
@RequestMapping("admin/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	StorageService storageService;
	
	@GetMapping("create")
	public String add(Model model) {
		CustomerDTO dto = new CustomerDTO();
		
		dto.setIsEdit(false);

		model.addAttribute("user", dto);

		return "admin/users/create";
	}

	@GetMapping("edit/{customerId}")
	public ModelAndView edit(ModelMap model, @PathVariable("customerId") Long customerId) {

		Optional<Customer> opt = userService.findById(customerId);
		CustomerDTO dto = new CustomerDTO();

		if (opt.isPresent()) {
			Customer entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			
			dto.setIsEdit(true);

			model.addAttribute("user", dto);

			return new ModelAndView("admin/users/create", model);
		}

		model.addAttribute("message", "User is not exsited");

		return new ModelAndView("forward:/admin/users", model);
	}

	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAndResources(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("delete/{customerId}")
	public ModelAndView delete(ModelMap model, @PathVariable("customerId") Long customerId) throws IOException {

		Optional<Customer> opt = userService.findById(customerId);

		if (opt.isPresent()) {
			if (!StringUtils.isEmpty(opt.get().getImage())) {
				storageService.delete(opt.get().getImage());
			}

			userService.delete(opt.get());

			model.addAttribute("message", "Deleted Success!");
		} else {
			model.addAttribute("message", "Deleted Failed!");
		}

		return new ModelAndView("forward:/admin/users", model);
	}

	@PostMapping("update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("user") CustomerDTO dto, BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/users/create");
		}

		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity);

		

		if (dto.getImageFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();

			String uuString = uuid.toString();

			entity.setImage(storageService.getStoredFilename(dto.getImageFile(), uuString));

			storageService.store(dto.getImageFile(), entity.getImage());
		}

		userService.save(entity);

		model.addAttribute("message", "User is saved!");

		return new ModelAndView("forward:/admin/users", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Customer> list = userService.findAll();

		model.addAttribute("user", list);

		return "admin/users/list";
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {

		List<Customer> list = null;

		if (StringUtils.hasText(name)) {
			list = userService.findByNameContaining(name);
		} else {
			list = userService.findAll();
		}

		model.addAttribute("user", list);

		return "admin/users/search";
	}

	@GetMapping("searchpaginated")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));

		Page<Customer> resultPage = null;

		if (StringUtils.hasText(name)) {
			resultPage = userService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = userService.findAll(pageable);
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

		model.addAttribute("userPage", resultPage);

		return "admin/users/searchpaginated";
	}

}
