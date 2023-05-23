package com.cndinfo.controller;

import java.sql.SQLDataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.cndinfo.domain.Alert;
import com.cndinfo.domain.User;
import com.cndinfo.service.UserService;
import com.cndinfo.util.DateUtil;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	UserService userService;
	DateUtil dateUtil;
	
	public UserController(UserService userService, DateUtil dateUtil) {
		this.userService = userService;
		this.dateUtil = dateUtil;
	}
	
	@PostMapping("/user/save")
	public String userSave(String name, String email, String pw, String telecom, String phone_number, Model model) {
		String message = "";
		String url = "";
		try {
			userService.userSave(new User(name, email, pw, telecom, phone_number, dateUtil.createDate(), null, "USER", "N'"));
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
