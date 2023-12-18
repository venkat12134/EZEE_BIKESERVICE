package org.in.com.io;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class UserIO {

	private String code;
	private String username;
	private String password;
	private int token;
	private String firstName;
	private String lastName;
	private long mobileNumber;
	private String emailId;
	private int activeFlag;
	private DateTime updatedAt;

}
