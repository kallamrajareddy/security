package com.kallam.api.security.user.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kallam.api.security.user.model.User;
import com.kallam.api.security.user.service.UserService;



@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_SUPER')")
	public User addUser(@RequestBody User user) {
		return this.userService.saveUser(user);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_SUPER') or hasAuthority('ROLE_ADMIN')")
	public List<User> getAllUsers() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
			String username = userDetails.getUsername();
			System.out.println(username);
		return this.userService.getAll();
	}
	
	@GetMapping("/user-details")
	@PreAuthorize("hasAuthority('ROLE_SUPER') or hasAuthority('ROLE_ADMIN')  or hasAuthority('ROLE_USER')")
	public User getUser(HttpServletRequest req) {
		return this.userService.findByUsername(req.getUserPrincipal().getName());
	}
	
	@GetMapping("/user-info")
	public Principal getAuthUser(HttpServletRequest req) {
		return req.getUserPrincipal();
	}

}
