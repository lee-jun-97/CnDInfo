package com.cndinfo.controller;

import java.sql.SQLDataException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cndinfo.domain.Alert;
import com.cndinfo.domain.User;
import com.cndinfo.service.UserService;
import com.cndinfo.util.DateUtil;
import com.cndinfo.util.SecurityUtil;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	private DateUtil dateUtil;
	private BCryptPasswordEncoder encoder;
	private SecurityUtil securityUtil;
	
	
	public UserController(UserService userService, DateUtil dateUtil, SecurityUtil securityUtil, BCryptPasswordEncoder encoder) {
		this.userService = userService;
		this.dateUtil = dateUtil;
		this.securityUtil = securityUtil;
		this.encoder = encoder;
	}
	
	@PostMapping("/user/save")
	public String userSave(String name, String email, String pw, String telecom, String phone, Model model) {
		String msg = "";
		String url = "";
		try {
			userService.userSave(new User(name, email, convertPw(pw), telecom, phone, dateUtil.createDate(), null, "USER"));
			logger.info("{} 회원 정보 저장 완료", name);
			msg = "회원 가입이 완료되었습니다.";
			url = "/";
		} catch(SQLDataException e) {
			logger.warn("이미 Email이 존재합니다.");
			msg = "이미 Email이 존재합니다.";
			url = "/join";
		} finally {
			model.addAttribute("params", new Alert(msg, url));
		}
		
		return "common/alert.html";
	}
	
	@PostMapping("/user/checkpw")
	public String checkpw(String pw, Model model) {
		
		String msg = "";
		String url = "";
		
		if(matchesPw(pw, userService.findUser(securityUtil.getEmail()).get().getPw())) {
			msg = "마이 페이지로 이동하겠습니다.";
			url = "/mypage";
		} else {
			msg = "비밀번호가 일치하지 않습니다.";
			url = "/checkpw";
		}
		
		model.addAttribute("params", new Alert(msg, url));
		
		return "common/alert";
	}
	
	
	@GetMapping("/mypage")
	public String myPage(Model model) {
		
		Optional<User> user = userService.findUser(securityUtil.getEmail());
		
		model.addAttribute("user", user.get());
		
		return "mypage";
	}
	
	@PostMapping("/user/modify")
	public String userModify(String name, String email, String pw, String telecom, String phone, Model model) {
		
		Optional<User> user = userService.findUser(email);
		
		String msg = "";
		String url = "";
		
		if(matchesPw(pw, user.get().getPw())) {
			if(user.get().getTelecom().equals(telecom) && user.get().getPhone().equals(phone)) {
				msg = "변경 사항이 없습니다.";
				url = "/mypage";
			} else {
				msg = "현재 비밀번호와 일치합니다.";
				url = "/mypage";
			}
		} else {
			userService.userUpdate(email, convertPw(pw), telecom, phone);
			logger.info("{} 회원 정보 수정 완료", email);
			msg = "수정이 완료되었습니다.";
			url = "/";
		}
		
		model.addAttribute("params", new Alert(msg, url));
		
		return "common/alert";
	}
	
	public boolean matchesPw(String inputPw, String dbPw) {
		return encoder.matches(inputPw, dbPw);
	}
	
	private String convertPw(String pw) {
		return encoder.encode(pw);
	}
	
}
