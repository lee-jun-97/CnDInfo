package com.cndinfo.controller;

import java.sql.SQLDataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.cndinfo.domain.Alert;
import com.cndinfo.domain.Member;
import com.cndinfo.service.MemberService;
import com.cndinfo.util.DateUtil;

@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberService;
	private DateUtil dateUtil;
	private BCryptPasswordEncoder pwEncoder;
	
	
	public MemberController(MemberService memberService, DateUtil dateUtil, BCryptPasswordEncoder pwEncoder) {
		this.memberService = memberService;
		this.dateUtil = dateUtil;
		this.pwEncoder = pwEncoder;
	}
	
	@PostMapping("/user/save")
	public String memberSave(String name, String email, String pw, String telecom, String phone, Model model) {
		String message = "";
		String url = "";
		try {
			memberService.memberSave(new Member(name, email, pwEncoder.encode(pw), telecom, phone, dateUtil.createDate(), null, "ROLE_USER"));
			logger.info("회원 정보 저장 완료");
			message = "회원 가입이 완료되었습니다.";
			url = "/";
		} catch(SQLDataException e) {
			logger.warn("이미 Email이 존재합니다.");
			message = "이미 Email이 존재합니다.";
			url = "/join";
		} finally {
			model.addAttribute("params", new Alert(message, url));
		}
		
		return "common/alert.html";
	}
	
}
