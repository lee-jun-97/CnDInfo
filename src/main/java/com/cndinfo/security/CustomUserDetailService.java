package com.cndinfo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cndinfo.domain.User;
import com.cndinfo.repository.UserRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

	private UserRepository userRepo;
	
	public CustomUserDetailService (UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		logger.info("input Data : {}", email);
		User user_temp = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		
		return user_temp;
		
	}
	
}
