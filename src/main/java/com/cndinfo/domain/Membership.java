package com.cndinfo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Membership {

	@Id
	public String brand_name;
	public String category_id;
	public String category_name;
	public String brand_id;
	public String telecom;
	
	public Membership() {
		
	}
}
