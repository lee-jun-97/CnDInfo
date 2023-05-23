package com.cndinfo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cndinfo.domain.SMSCode;
import com.cndinfo.repository.SMSCodeRedisRepository;

@Service
public class CertificationService {
	
	SMSCodeRedisRepository smsRepo;
	
	public CertificationService(SMSCodeRedisRepository smsRepo) {
		this.smsRepo = smsRepo;
	}

	public void saveCertifiactionNumber(SMSCode smsCode) throws UsernameNotFoundException {
		if(smsRepo.findByPhone(smsCode.getPhone()).get().getCode() == null) {
			smsRepo.save(smsCode);
		} else {
			new UsernameNotFoundException("등록된 전화번호가 없습니다.");
		}
	}
	
	public boolean checkCertificationNumber(String phone, String code) {
		return smsRepo.findById(phone).get().getCode()==code?true:false;
	}
}
