package com.letsTravel.LetsTravel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letsTravel.LetsTravel.domain.LoginDTO;
import com.letsTravel.LetsTravel.domain.MemberBasicInfoReadDTO;
import com.letsTravel.LetsTravel.service.MemberService;

@RestController
@RequestMapping(value = "/api")
public class MemberController {
	
	private final MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/login")
	public Optional<MemberBasicInfoReadDTO> login(LoginDTO loginDTO){
		return memberService.findMember(loginDTO);
	}
}
