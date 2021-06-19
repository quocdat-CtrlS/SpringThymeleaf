package com.blue.shop.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blue.shop.entity.Account;
import com.blue.shop.model.AdminLoginDTO;
import com.blue.shop.service.AccountServices;

@Controller
public class AdminLoginController {

	@Autowired
	private AccountServices accountService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("alogin")
	public String login(ModelMap model) {
		
		model.addAttribute("accounts", new AdminLoginDTO());
		
		return "/admin/accounts/login";
		
	}
	
	@PostMapping("alogin")
	public ModelAndView login(ModelMap model,
			@Valid @ModelAttribute("accounts") AdminLoginDTO dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("/admin/accounts/login", model);
		}
		
		Account account = accountService.login(dto.getUsername(), dto.getPassword());
		
		if (account == null) {
			model.addAttribute("message", "Invalid username or password!");
			
			return new ModelAndView("/admin/accounts/login", model);
		}
		
		session.setAttribute("username", account.getUsername());
		
		return new ModelAndView("forward:/admin/categories", model);
		
	}
}
