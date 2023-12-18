package org.in.com.controller;


import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.io.CustomerIO;
import org.in.com.service.AuthService;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customer/{authtoken}")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	AuthService authService;
	

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CustomerIO updateCustomer(@PathVariable("authtoken") String authtoken, @RequestBody CustomerIO customerIO) throws Exception {

		AuthDTO authDTO = null;
		authDTO = authService.getUserByToken(authtoken);
		CustomerIO customerIo = new CustomerIO();

		if (authDTO != null) {
			CustomerDTO customerDto = new CustomerDTO();
			customerDto.setCode(customerIO.getCode());
			customerDto.setBikeNumber(customerIO.getBikeNumber());
			customerDto.setAddress(customerIO.getAddress());
			customerDto.setBikeModel(customerIO.getBikeModel());
			customerDto.setFirstName(customerIO.getFirstName());
			customerDto.setLastName(customerIO.getLastName());
			customerDto.setMobileNumber(customerIO.getMobileNumber());
			customerDto.setEmailId(customerIO.getEmailId());
			customerDto.setActiveFlag(customerIO.getActiveFlag());
			CustomerDTO customerDTO = customerService.updateCustomer(authDTO, customerDto);
			customerIo.setActiveFlag(customerDTO.getActiveFlag());
			customerIo.setCode(customerDTO.getCode());
		}
		return customerIo;
	}
	
	@RequestMapping(value = "/history", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
	public List<CustomerIO> getCustomerHistory(@PathVariable("authtoken") String authtoken) throws Exception {
  
		AuthDTO authDTO =null;
		authDTO = authService.getUserByToken(authtoken);
	    List<CustomerIO> customerIO = new ArrayList<CustomerIO>();

		if(authDTO !=null){
		List<CustomerDTO> customerDto = customerService.getCustomerHistory(authDTO);
		
		for (CustomerDTO customerDTO : customerDto) {
			CustomerIO customerObj = new CustomerIO();
			customerObj.setCode(customerDTO.getCode());
			customerObj.setBikeNumber(customerDTO.getBikeNumber());
			customerObj.setAddress(customerDTO.getAddress());
			customerObj.setBikeModel(customerDTO.getBikeModel());
			customerObj.setFirstName(customerDTO.getFirstName());
			customerObj.setLastName(customerDTO.getLastName());
			customerObj.setMobileNumber(customerDTO.getMobileNumber());
			customerObj.setEmailId(customerDTO.getEmailId());
			customerObj.setActiveFlag(customerDTO.getActiveFlag());
			customerIO.add(customerObj);
		}			
		}
		return customerIO;
	
	}
	@RequestMapping(value="/{code}",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public CustomerIO getCustomer(@PathVariable("authtoken") String authtoken, @PathVariable String code) throws Exception {
		AuthDTO authDTO = null;
		authDTO = authService.getUserByToken(authtoken);
		CustomerIO customerIo = new CustomerIO();

		if (authDTO != null) {
			CustomerDTO customerDto = new CustomerDTO();
			customerDto.setCode(code);
			customerDto = customerService.getCustomer(customerDto, authDTO);
			customerIo.setCode(customerDto.getCode());
			customerIo.setBikeNumber(customerDto.getBikeNumber());
			customerIo.setAddress(customerDto.getAddress());
			customerIo.setBikeModel(customerDto.getBikeModel());
			customerIo.setFirstName(customerDto.getFirstName());
			customerIo.setLastName(customerDto.getLastName());
			customerIo.setMobileNumber(customerDto.getMobileNumber());
			customerIo.setEmailId(customerDto.getEmailId());
			customerIo.setActiveFlag(customerDto.getActiveFlag());
		}
		return customerIo;

	}

}
