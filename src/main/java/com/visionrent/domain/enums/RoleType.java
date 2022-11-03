package com.visionrent.domain.enums;

public enum RoleType {
	
	ROLE_CUSTOMER("Customer"),
	ROLE_ADMIN("Administrator");
	
	private String name;
	
	//constructor'i disari acmamak icin private yapiyoruz
	private RoleType(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

}
