package com.cndinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cndinfo.domain.MembershipDetail;

@EnableJpaRepositories
public interface MembershipDetailRepository extends JpaRepository<MembershipDetail, String>{
	List<MembershipDetail> findByBrandName(String brand_name);
}
