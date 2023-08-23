package com.cndinfo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "membership_brands")
public class Membership {

	@Id
	public String brandName;
	public String categoryId;
	public String categoryName;
	public String brandId;
	public String telecom;
	
}
