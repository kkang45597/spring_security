package com.mingi.Ch8_1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

	@GetMapping("/email/{email}")
	public String getEmail(@PathVariable String email) {
		return "Sended email: " + email;
	}
}
