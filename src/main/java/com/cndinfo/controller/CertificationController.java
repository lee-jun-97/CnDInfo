package com.cndinfo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cndinfo.domain.Alert;
import com.cndinfo.repository.SMSCodeRedisRepository;
import com.cndinfo.service.CertificationService;

@Controller
public class CertificationController {
	
	private static final Logger logger = LoggerFactory.getLogger(CertificationController.class);
	
	public CertificationService certiService;
	public SMSCodeRedisRepository smsRepo;
	
	public CertificationController(CertificationService certiService, SMSCodeRedisRepository smsRepo) {
		this.certiService = certiService;
		this.smsRepo = smsRepo;
	}
	
	@GetMapping("/req_certification")
	public String reqCerti(String telecom, String phone, Model model) {
		String code = String.valueOf((int)(Math.random() * 899999) + 100000);
		model.addAttribute("params", new Alert("인증번호를 입력해주세요", "/"));
		return "common/certiAlert";
	}
	
	@GetMapping("/check_certification")
	public void checkCerti(String phone, String code) {
		System.out.println(certiService.checkCertificationNumber(phone, code));
	}

}
