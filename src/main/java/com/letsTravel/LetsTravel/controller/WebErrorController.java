package com.letsTravel.LetsTravel.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebErrorController implements ErrorController{
	
	@GetMapping({"/", "/error"})
	public String errorHtml() {
		return "index.html";
	}
}
