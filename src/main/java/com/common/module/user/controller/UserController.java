package com.common.module.user.controller;

import java.util.Collection;

import com.common.auth.service.CustomUserDetails;
import com.common.module.user.dto.User;
import com.common.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/list")
	public void getAllUser(Model model) {
		model.addAttribute("listUsers", userService.getAll());
	}

	@GetMapping("/users")
	public void users(Model model) {
		model.addAttribute("listUsers", userService.getAll());
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		userService.save(user);
		return "register_success";
	}

}
