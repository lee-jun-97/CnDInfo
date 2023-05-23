package com.cndinfo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User {
	
	@Id
	public String name;
	public String email;
	public String pw;
	public String telecom;
	public String phone_number;
	public String join_date;
	public String dead_date = null;
	public String role;
	public String isCerti;

	public User() {
		
	}
	
	public User(String name, String email, String pw, String telecom, String phone_number, String join_date,
			String dead_date, String role, String isCerti) {
		super();
		this.name = name;
		this.email = email;
		this.telecom = telecom;
		this.phone_number = phone_number;
		this.pw = pw;
		this.join_date = join_date;
		this.dead_date = dead_date==null?null:dead_date;
		this.role = role;
		this.isCerti = "N";
	}
	
	

}
