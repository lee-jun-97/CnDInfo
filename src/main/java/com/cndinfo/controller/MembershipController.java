package com.cndinfo.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cndinfo.domain.Membership;
import com.cndinfo.service.CertificationService;
import com.cndinfo.service.MembershipService;
import com.cndinfo.util.SecurityUtil;

@Controller
public class MembershipController {
	
	private CertificationService certificationService;
	private MembershipService membershipService;
	private SecurityUtil securityUtil;
	
	public MembershipController(CertificationService certificationService, MembershipService membershipService, SecurityUtil securityUtil) {
		this.membershipService = membershipService;
		this.securityUtil = securityUtil;
		this.certificationService = certificationService;
	}

	@GetMapping("/membership")
	@ResponseBody
	public List<Membership> membership(Model model,
			@RequestParam(value = "page", defaultValue = "0")String page) {
		
//		if(certificationService.checkIsCerti(securityUtil.getEmail()).equals("N")) {
//			String msg = "인증이 완료되지 않았습니다. 인증을 먼저 완료해주세요.";
//			String url = "/";
//			
//			model.addAttribute("params", new Alert(msg, url));
//			
//			return "common/alert";
//		}
//		
		String telecom = membershipService.getTelecom();
		// pageabel 기본 default 값이 20으로 지정됨.
		// page는 Parameter로 받고 size는 10으로 고정.
		PageRequest pr = PageRequest.of(Integer.parseInt(page), 10);
//		model.addAttribute("membership", membershipService.getMembership(membershipService.getTelecom(), pr));
//		model.addAttribute("membership", membershipService.getMembership(membershipService.getTelecom()));
//		return "service_".concat(telecom.toLowerCase());
		
		return membershipService.getMembership(membershipService.getTelecom());
	}
}
