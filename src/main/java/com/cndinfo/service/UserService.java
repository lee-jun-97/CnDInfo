package com.cndinfo.service;

import java.sql.SQLDataException;

import org.springframework.stereotype.Service;

import com.cndinfo.domain.User;
import com.cndinfo.repository.UserRepository;

@Service
public class UserService {
	
	UserRepository userRepo;
	
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	public void userSave(User user) throws SQLDataException {
		if(userRepo.findByEmail(user.getEmail()) == null) {
			userRepo.save(user);
		} else {
			new SQLDataException();
		}
	}

}
