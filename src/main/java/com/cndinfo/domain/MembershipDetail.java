package com.cndinfo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table( name = "membership_brands_detail")
public class MembershipDetail {
	
	public String telelcom;
	public String brandId;
	@Id
	public String brandName;
	public String membershipType;
	public String grade;
	public String detail;

}
