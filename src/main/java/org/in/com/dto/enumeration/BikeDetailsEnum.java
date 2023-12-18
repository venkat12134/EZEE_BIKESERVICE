package org.in.com.dto.enumeration;

public enum BikeDetailsEnum {

	Initial("name"), 
	Inprogress("name"),
	Completed("name");
	
	public final String name;
	
	private BikeDetailsEnum(String name) {
		this.name = name;
	}

}
