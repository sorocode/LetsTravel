package com.letsTravel.LetsTravel.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

public class TempErrorController implements ErrorController{

	@RequestMapping("/error")
	public RedirectView handleError() {
		return new RedirectView("/");
	}
}
