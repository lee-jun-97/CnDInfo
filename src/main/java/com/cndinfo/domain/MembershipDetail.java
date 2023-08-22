package com.cndinfo.domain;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class MembershipDetail {
	
	public String telelcom;
	public String brand_id;
	public String brand_name;
	public String membership_type;
	public String grade;
	public String detail;
	
	public MembershipDetail() {
		
	}

}
