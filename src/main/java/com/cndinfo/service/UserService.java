package com.cndinfo.service;

import java.sql.SQLDataException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cndinfo.domain.User;
import com.cndinfo.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepo;
	
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	public void userSave(User user) throws SQLDataException {
		
		Optional<User> user_temp = userRepo.findByEmail(user.getEmail());
		if(user_temp.isEmpty()) {
			userRepo.save(user);
		} else {
			throw new SQLDataException();
		}
	}
	
	public Optional<User> findUserOne(String email) {
		return userRepo.findByEmail(email);
	}
	
	public void userUpdate(String email, String pw, String telecom, String phone) {
		userRepo.updateUser(email, pw, telecom, phone);
	}

}
