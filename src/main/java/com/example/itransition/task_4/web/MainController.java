package com.example.itransition.task_4.web;

import com.example.itransition.task_4.model.User;
import com.example.itransition.task_4.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
	private UserService userService;

	public MainController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users",users);
		return "index";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/deleteData")
	public String delete(@RequestParam(value="myArray[]") List<Long> myArray){
		userService.deleteUsers(myArray);
		return "index";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/blockUser")
	public String block(@RequestParam(value="myArray[]") List<Long> myArray){
		userService.blockUser(myArray);
		return "index";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/unblockUser")
	public String unblock(@RequestParam(value="myArray[]") List<Long> myArray){
		userService.unblockUser(myArray);
		return "index";
	}
}
