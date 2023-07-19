package com.cndinfo.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "members")
public class Member implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	public String email;
	public String name;
	public String pw;
	public String telecom;
	public String phone;
	public String join_date;
	public String dead_date = null;
	public String role;
	public String iscerti;

	public Member() {
		
	}
	
	public Member(String name, String email, String pw, String telecom, String phone, String join_date,
			String dead_date, String role) {
		this.name = name;
		this.email = email;
		this.pw = pw;
		this.telecom = telecom;
		this.phone = phone;
		this.join_date = join_date;
		this.dead_date = dead_date==null?null:dead_date;
		this.role = role;
		this.iscerti = "N";
	}
	
	public String toString() {
		return "Member : [ " + this.getEmail() + ", " + this.getPassword() + " ]"; 
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority(this.role));
		
		return auth;
	}

	@Override
	public String getPassword() {
		return this.pw;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
