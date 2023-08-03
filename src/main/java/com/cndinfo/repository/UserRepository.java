package com.cndinfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cndinfo.domain.User;

@Repository	
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE User u SET u.iscerti = :certi WHERE u.email = :email")
	int updateIsCerti(@Param("email")String email, @Param("certi")String certi);
}
