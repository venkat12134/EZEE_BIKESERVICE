package org.in.com.service;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;

public interface CustomerService {

	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerObj);
	public List<CustomerDTO> getCustomerHistory(AuthDTO authDTO);
	public CustomerDTO getCustomer(CustomerDTO customer, AuthDTO authDTO);
	public CustomerDTO getCustomerById(AuthDTO authDTO, CustomerDTO customer);
	public List<CustomerDTO> getCustomerByIdList(AuthDTO authDTO, List<Integer> customer);
}
