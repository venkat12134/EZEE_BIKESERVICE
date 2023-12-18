package org.in.com.dto;


import org.in.com.dto.enumeration.BikeDetailsEnum;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class BikeDetailsDTO {

	private int id;
	private String code; 
	private NamespaceDTO namespace;
	private CustomerDTO customer;
	private String issueDetails;
	private DateTime bookedAt;
	private BikeDetailsEnum StatusId;
	private DateTime deliveryAt;
	private int transactionAmount; 
	private int activeFlag; 
	private UserDTO updatedBy;
	private DateTime updatedAt;
	
}
