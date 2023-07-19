package com.cndinfo.service;

import java.sql.SQLDataException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cndinfo.domain.Member;
import com.cndinfo.repository.MemberRepository;

@Service
public class MemberService {
	
	private MemberRepository memberRepo;
	
	public MemberService(MemberRepository memberRepo) {
		this.memberRepo = memberRepo;
	}
	
	public void memberSave(Member member) throws SQLDataException {
		
		Optional<Member> member_temp = memberRepo.findByEmail(member.getEmail());
		if(member_temp.isEmpty()) {
			memberRepo.save(member);
		} else {
			throw new SQLDataException();
		}
	}

}
