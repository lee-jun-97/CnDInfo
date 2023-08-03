package com.cndinfo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.RedisTemplate;

@DataRedisTest
public class CertificationServiceTest {

	@Autowired
	RedisTemplate<String, String> smsRepo;
	
	@Test
	public void save() {
		
		smsRepo.opsForValue().set("test", "000000");
		smsRepo.opsForValue().set("test1", "111111");
		
		Assertions.assertEquals(smsRepo.opsForValue().get("test"), "000000");
		Assertions.assertEquals(smsRepo.opsForValue().get("test1"), "111111");
		
		smsRepo.delete("test");
		smsRepo.delete("test1");
	}

}
