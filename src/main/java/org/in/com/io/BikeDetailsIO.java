package org.in.com.io;


import org.in.com.dto.enumeration.BikeDetailsEnum;

import hirondelle.date4j.DateTime;
import lombok.Data;

@Data
public class BikeDetailsIO {

	private String code;
	private CustomerIO customer;
	private String issueDetails;
	private BikeDetailsEnum StatusId;
	private String bookedAt;
	private String deliveryAt;
	private int transactionAmount;
	private int activeFlag;
	private DateTime updatedAt;

}
