package com.te.groupchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.groupchat.model.OurUser;
import com.te.groupchat.repository.OurUserRepo;
import com.te.groupchat.repository.ProductRepo;

@RestController
@RequestMapping
public class Controller {
	@Autowired
	private OurUserRepo ourUserRepo;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String goH0me() {
		return "Thisn is publickly accesible withing needing authentication ";
	}

	@PostMapping("/user/save")
	public ResponseEntity<Object> saveUSer(@RequestBody OurUser ourUser) {
		ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
		OurUser result = ourUserRepo.save(ourUser);
		if (result.getId() > 0) {
			return ResponseEntity.ok("USer Was Saved");
		}
		return ResponseEntity.status(404).body("Error, USer Not Saved");
	}

	@GetMapping("/product/all")
	public ResponseEntity<Object> getAllProducts() {
		return ResponseEntity.ok(productRepo.findAll());
	}

	@GetMapping("/users/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> getAllUSers() {
		return ResponseEntity.ok(ourUserRepo.findAll());
	}

	@GetMapping("/users/single")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Object> getMyDetails() {
		return ResponseEntity.ok(ourUserRepo.findByEmail(getLoggedInUserDetails().getUsername()));
	}

	public UserDetails getLoggedInUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			return (UserDetails) authentication.getPrincipal();
		}
		return null;
	}

	@GetMapping("/user/welcome")
	public String getMyName() {

		return "Welcome" + getLoggedInUserDetails().getUsername();
	}
}
