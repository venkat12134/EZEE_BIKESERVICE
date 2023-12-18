package org.in.com.io;

import hirondelle.date4j.DateTime;
import lombok.Data;


@Data
public class NamespaceIO {

	
	private String code;
	private String name;
	private String address;	
	private int activeFlag;
	private DateTime updatedAt;
	
}
