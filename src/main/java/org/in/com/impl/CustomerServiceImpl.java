package org.in.com.impl;

import java.util.List;
import org.in.com.dao.CustomerDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.rediscache.CustomerCacheManager;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
    private CustomerDAO customerDao;

	@Autowired
	private CustomerCacheManager customerCacheManager;	
	

	@Override
	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerObj) {
		try{
			customerObj = customerDao.updateCustomer(authDTO, customerObj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return  customerObj;
	}

	@Override
	public List<CustomerDTO> getCustomerHistory(AuthDTO authDTO) {
      List<CustomerDTO> customer = null;
		try{
			customer = customerDao.getCustomerHistory(authDTO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public CustomerDTO getCustomer(CustomerDTO customer,AuthDTO authDTO) {
		CustomerDTO customerDto = customerCacheManager.getCustomerData(customer);
		if (customerDto == null)
			try {
				customerDto = customerDao.getCustomer(authDTO, customer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		customerCacheManager.putCustomerData(customer, customerDto);
		System.out.println("data stored in redis cache" + customerDto);

		return customerDto;

	}

	@Override
	public CustomerDTO getCustomerById(AuthDTO authDTO, CustomerDTO customer) {
		try{
			customer = customerDao.getCustomerById(authDTO, customer);
		}catch(Exception e){
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public List<CustomerDTO> getCustomerByIdList(AuthDTO authDTO, List<Integer> customerDto) {

		List<CustomerDTO> customer = null;
		try {
			customer = customerDao.getCustomerByIdList(authDTO, customerDto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return customer;
	}
	
}


  
