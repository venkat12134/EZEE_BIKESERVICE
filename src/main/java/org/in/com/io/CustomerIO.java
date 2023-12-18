package org.in.com.io;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class CustomerIO {

	private String code;
	private String bikeNumber;
	private String address; 
	private String bikeModel; 
	private String firstName; 
	private String lastName; 
	private Long mobileNumber; 
	private String emailId;
	private int activeFlag; 
	private DateTime updatedAt;
	
	
}
