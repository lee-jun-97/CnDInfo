package com.cndinfo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cndinfo.domain.Membership;
import com.cndinfo.repository.MembershipDetailRepository;
import com.cndinfo.repository.MembershipRepository;
import com.cndinfo.repository.UserRepository;
import com.cndinfo.util.SecurityUtil;

@Service
public class MembershipService {
	
	private UserRepository userRepo;
	private MembershipRepository membershipRepo;
	private MembershipDetailRepository membershipDetailRepo;
	private SecurityUtil securityUtil;
	
	public MembershipService(UserRepository userRepo, MembershipRepository membershipRepo, MembershipDetailRepository membershipDetailRepo, SecurityUtil securityUtil) {
		this.userRepo = userRepo;
		this.membershipRepo = membershipRepo;
		this.membershipDetailRepo = membershipDetailRepo;
		this.securityUtil = securityUtil;
	}
	
	public String getTelecom() {
		return userRepo.findByEmail(securityUtil.getEmail()).get().getTelecom();
	}
	
	public List<Membership> getMembership(String telecom, Pageable pageable) {
		return membershipRepo.findByTelecom(telecom, pageable).getContent();
	}
	
	public List<Membership> getMembership(String telecom) {
		return membershipRepo.findByTelecom(telecom);
	}

}
