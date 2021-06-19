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

import com.blue.shop.entity.Account;
import com.blue.shop.entity.Account;
import com.blue.shop.model.AccountDTO;
import com.blue.shop.service.AccountServices;
import com.blue.shop.service.CategoryService;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {

	@Autowired
	AccountServices accountServices;

	@GetMapping("create")
	public String add(Model model) {
		model.addAttribute("accounts", new AccountDTO());
		
		return "admin/accounts/create";
	}
	
	@PostMapping("update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("accounts") AccountDTO dto,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/accounts/create");
		}

		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);

		accountServices.save(entity);

		model.addAttribute("message", "Account is saved!");

		return new ModelAndView("forward:/admin/accounts", model);
	}
	
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Account> list = accountServices.findAll();

		model.addAttribute("accounts", list);

		return "admin/accounts/list";
	}


	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {

		Optional<Account> opt = accountServices.findById(username);
		
		AccountDTO dto = new AccountDTO();

		if (opt.isPresent()) {
			Account entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			dto.setPassword("");

			model.addAttribute("accounts", dto);

			return new ModelAndView("admin/accounts/create", model);
		}

		model.addAttribute("message", "Account is not exsited");

		return new ModelAndView("forward:/admin/accounts", model);
	}

	@GetMapping("delete/{username}")
	public ModelAndView delete(ModelMap model, @PathVariable("username") String username) {

		accountServices.deleteById(username);

		model.addAttribute("message", "Deleted Success!");

		return new ModelAndView("forward:/admin/accounts", model);
	}



//
//	@GetMapping("search")
//	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
//
//		List<Account> list = null;
//
//		if (StringUtils.hasText(name)) {
//			list = cateServices.findByNameContaining(name);
//		} else {
//			list = cateServices.findAll();
//		}
//
//		model.addAttribute("accounts", list);
//
//		return "admin/accounts/search";
//	}
//
//	@GetMapping("searchpaginated")
//	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name,
//			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
//		
//		int currentPage = page.orElse(1);
//		int pageSize = size.orElse(5);
//		
//		Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
//		
//		Page<Account> resultPage = null;
//
//		if (StringUtils.hasText(name)) {
//			resultPage = cateServices.findByNameContaining(name, pageable);
//			model.addAttribute("name", name);
//		} else {
//			resultPage = cateServices.findAll(pageable);
//		}
//		
//		int totalPages = resultPage.getTotalPages();
//		
//		if (totalPages > 0) {
//			int start = Math.max(1, currentPage-2);
//			int end = Math.min(currentPage + 2, totalPages);
//			
//			if (totalPages > 5) {
//				if (end == totalPages) start = end - 5;
//				else if (start == 1) end = start + 5;
//			}
//			
//			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
//					.boxed()
//					.collect(Collectors.toList());
//			
//			model.addAttribute("pageNumbers", pageNumbers);
//		}
//
//		model.addAttribute("categoryPage", resultPage);
//
//		return "admin/accounts/searchpaginated";
//	}

}
