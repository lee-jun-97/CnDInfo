package com.cndinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cndinfo.service.MembershipService;

@Controller
public class MembershipController {
	
	private MembershipService membershipService;
	
	public MembershipController(MembershipService membershipService) {
		this.membershipService = membershipService;
	}

	@GetMapping("/membership")
	public String membership(Model model) {
		String telecom = membershipService.getTelecom();
		model.addAttribute("membership", membershipService.getMembership(membershipService.getTelecom()));
		return "service_".concat(telecom.toLowerCase());
	}
}
