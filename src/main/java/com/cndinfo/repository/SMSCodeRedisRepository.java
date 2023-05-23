package com.cndinfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cndinfo.domain.SMSCode;

import jakarta.transaction.Transactional;

@EnableRedisRepositories
public interface SMSCodeRedisRepository extends CrudRepository<SMSCode, String>{
	
	@Transactional
	@Modifying
	@Query("UPDATE SMSCode smsCode SET smsCode.code = :code WHERE smsCode.telecom = :telecom AND smsCode.phone = :phone")
	void updateCode(@Param("telectom") String telecom, @Param("phone") String phone, @Param("code") String code);
	
	Optional<SMSCode> findByPhone(String phone);
}
