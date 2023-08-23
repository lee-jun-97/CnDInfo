package com.cndinfo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cndinfo.domain.Membership;

@EnableJpaRepositories
public interface MembershipRepository extends JpaRepository<Membership, Long>{
	Page<Membership> findByTelecom(String telecom, Pageable pageable);
	List<Membership> findByTelecom(String telecom);
}
