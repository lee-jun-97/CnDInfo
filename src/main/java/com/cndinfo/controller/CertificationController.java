package com.cndinfo.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cndinfo.domain.Alert;
import com.cndinfo.service.CertificationService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class CertificationController {
	
	private static final Logger logger = LoggerFactory.getLogger(CertificationController.class);
	
	private CertificationService certiService;
	
	public CertificationController(CertificationService certiService) {
		this.certiService = certiService;
	}
	
	@GetMapping("/certi")
	public String certi(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = String.valueOf(auth.getPrincipal());
        logger.info("{} 인증 절차 진행", email);
        String msg;
        String url;
        
        if(certiService.checkIsCerti(email).equals("Y")) {
        	msg = "이미 인증이 완료되었습니다.";
        	url = "/";
        } else {
        	msg = "SMS 인증을 진행하겠습니다.";
        	url = "/certi/req";
        }
        
        model.addAttribute("params", new Alert(msg, url));
        
        return "common/alert";
	}
	
	// Service Layer에서 Redis에 난수(code) 와 email 저장.
	@GetMapping("/certi/req")
	public String reqCerti(Model model) throws JsonProcessingException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
		try {
			certiService.reqCerti(String.valueOf(auth.getPrincipal()), String.valueOf((int)(Math.random() * 899999) + 100000));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/certification";
	}

	// Redis에서 email을 Key 값으로 난수 가져온 후 비교하여 리턴 함.
	@PostMapping("/certi/check")
	public String checkCerti(String code, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = String.valueOf(auth.getPrincipal());

		String msg ;
		String url ;
		
		try {
			if(certiService.checkCertificationNumber(email, code)) {
				msg = "인증 완료 되었습니다.";
				url = "/";
				certiService.updateIsCerti(email);
				certiService.removeCode(email);
			} else {
				msg = "인증 번호가 다릅니다. 다시 한 번 입력해 주세요.";
				url = "/certification";
			}
		} catch (NoSuchElementException e) {
			msg = "인증 번호가 만료되었습니다. 인증 번호를 다시 발급 받아주세요.";
			url = "/certification";
		}
		
		model.addAttribute("params", new Alert(msg, url));
		
		return "common/alert";
	}

}
