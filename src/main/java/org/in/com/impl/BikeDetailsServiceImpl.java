package org.in.com.impl;

import java.util.ArrayList;
import java.util.List;

import org.in.com.dao.BikeDetailsDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.BikeDetailsDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.service.BikeDetailsService;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeDetailsServiceImpl implements BikeDetailsService {

	@Autowired
	BikeDetailsDAO bikeDetailsDao;

	@Autowired
	CustomerService customerService;

	@Override
	public BikeDetailsDTO updateBikeDetails(AuthDTO authDTO, BikeDetailsDTO bikeDetailsObj) {
		try {
			CustomerDTO customer = new CustomerDTO();
			String customerObj = bikeDetailsObj.getCustomer().getCode();
			customer.setCode(customerObj);
			customer = customerService.getCustomer(customer, authDTO);
			bikeDetailsObj.setCustomer(customer);
			bikeDetailsObj = bikeDetailsDao.updateCustomer(authDTO, bikeDetailsObj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bikeDetailsObj;
	}

	@Override
	public BikeDetailsDTO getBikeDetails(AuthDTO authDTO, BikeDetailsDTO bikeDetailsDTO) {
		bikeDetailsDTO = bikeDetailsDao.getBikeDetails(authDTO, bikeDetailsDTO);
		try {
			bikeDetailsDTO.setCustomer(customerService.getCustomerById(authDTO, bikeDetailsDTO.getCustomer()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bikeDetailsDTO;
	}
	@Override
	public List<BikeDetailsDTO> getBikeHistory(AuthDTO authDTO, String fromDate, String toDate) {
		List<BikeDetailsDTO> bikeDetailsDTO = null;
		try {
			bikeDetailsDTO = bikeDetailsDao.getBikeHistory(authDTO, fromDate, toDate);
			if (bikeDetailsDTO != null && !bikeDetailsDTO.isEmpty()) {
				int customerId = bikeDetailsDTO.get(0).getCustomer().getId();
				List<Integer> customerDTO = new ArrayList<>();
				customerDTO.add(customerId);
				List<CustomerDTO> customerDto = customerService.getCustomerByIdList(authDTO, customerDTO);
				if (customerDto != null && !customerDto.isEmpty()) {
					CustomerDTO customer = customerDto.get(0);
					customer.setId(customerId);
					for (BikeDetailsDTO bikeDetail : bikeDetailsDTO) {
						bikeDetail.setCustomer(customer);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bikeDetailsDTO;
	}
}

