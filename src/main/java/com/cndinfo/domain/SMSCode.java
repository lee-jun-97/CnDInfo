package com.cndinfo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@Getter
@RedisHash(value = "SMSCode", timeToLive= 180)
public class SMSCode {
	
	@Id
	private String phone;
	private String telecom;
	private String code;
	
	public SMSCode() {
		
	}
	
	public SMSCode(String phone, String telecom, String code) {
		this.phone = phone;
		this.telecom = telecom;
		this.code = code;
	}

}
