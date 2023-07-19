package com.cndinfo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cndinfo.domain.Member;
import com.cndinfo.repository.MemberRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

	private MemberRepository memberRepo;
	
	public CustomUserDetailService (MemberRepository memberRepo) {
		this.memberRepo = memberRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		logger.info("input Data : {}", email);
		Member member_temp = memberRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		
		return member_temp;
		
	}
	
}
