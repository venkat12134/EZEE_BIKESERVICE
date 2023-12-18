package org.in.com.controller;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.BikeDetailsDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.io.BikeDetailsIO;
import org.in.com.io.CustomerIO;
import org.in.com.service.AuthService;
import org.in.com.service.BikeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hirondelle.date4j.DateTime;

@Controller
@RequestMapping("/{authtoken}/bike/service")
public class BikeDetailsController {
	@Autowired
	BikeDetailsService bikeDetailService;
	
	@Autowired
	AuthService authService; 

	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BikeDetailsIO updateBikeDetails(@PathVariable("authtoken") String authtoken, @RequestBody BikeDetailsIO bikedetailsIO) throws Exception {
		AuthDTO authDTO = null;
		authDTO = authService.getUserByToken(authtoken);
		BikeDetailsIO bikeDetailsIo = new BikeDetailsIO();
		BikeDetailsDTO bikeDetailsObj = new BikeDetailsDTO();
		if (authDTO != null) {
			bikeDetailsObj.setCode(bikedetailsIO.getCode());
			String customerCode = bikedetailsIO.getCustomer().getCode();
			CustomerDTO customer = new CustomerDTO();
			customer.setCode(customerCode);
			bikeDetailsObj.setCustomer(customer);
			bikeDetailsObj.setIssueDetails(bikedetailsIO.getIssueDetails());
			bikeDetailsObj.setStatusId(bikedetailsIO.getStatusId());
			bikeDetailsObj.setTransactionAmount(bikedetailsIO.getTransactionAmount());
			bikeDetailsObj.setActiveFlag(bikedetailsIO.getActiveFlag());
			BikeDetailsDTO namespaceDTO = bikeDetailService.updateBikeDetails(authDTO, bikeDetailsObj);
			bikeDetailsIo.setActiveFlag(namespaceDTO.getActiveFlag());
			bikeDetailsIo.setCode(namespaceDTO.getCode());
		}
		return bikeDetailsIo;
	}
	 
	@RequestMapping(value="/{code}",method=RequestMethod.GET,produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public BikeDetailsIO getBikeDetails(@PathVariable("authtoken") String authtoken, @PathVariable String code) throws Exception {

		AuthDTO authDTO = null;
		authDTO = authService.getUserByToken(authtoken);
		BikeDetailsIO bikeDetailsIo = new BikeDetailsIO();
		if (authDTO != null) {
			BikeDetailsDTO bikeDetailsDto = new BikeDetailsDTO();
			bikeDetailsDto.setCode(code);
			bikeDetailsDto = bikeDetailService.getBikeDetails(authDTO, bikeDetailsDto);
			bikeDetailsIo.setCode(bikeDetailsDto.getCode());
		    CustomerIO customer = new CustomerIO();
			customer.setCode(bikeDetailsDto.getCustomer().getCode());
			bikeDetailsIo.setCustomer(customer);
			bikeDetailsIo.setIssueDetails(bikeDetailsDto.getIssueDetails());
			DateTime bookedAt = bikeDetailsDto.getBookedAt();
			String bookedAtObj = bookedAt.format("YYYY-MM-DD");
			bikeDetailsIo.setBookedAt(bookedAtObj);
			DateTime deliveryAt = bikeDetailsDto.getDeliveryAt();
			String deliveryAtObj = deliveryAt.format("YYYY-MM-DD");
			bikeDetailsIo.setDeliveryAt(deliveryAtObj);
			bikeDetailsIo.setStatusId(bikeDetailsDto.getStatusId());
			bikeDetailsIo.setTransactionAmount(bikeDetailsDto.getTransactionAmount());
			bikeDetailsIo.setActiveFlag(bikeDetailsDto.getActiveFlag());
		}
		return bikeDetailsIo;
	}

	@RequestMapping(value = "/history/{date1}/{date2}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<BikeDetailsIO> getHistory(@PathVariable String authtoken, @PathVariable Date date1, @PathVariable Date date2) throws Exception {
		AuthDTO authDTO = null;
		authDTO = authService.getUserByToken(authtoken);
		List<BikeDetailsIO> bikeDetailsIo = new ArrayList<BikeDetailsIO>();

		if (authDTO != null) {
			List<BikeDetailsDTO> bikeDetailsObj = new ArrayList<>();
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String fromDate = formatter.format(date1);
			String toDate = formatter.format(date2);
			bikeDetailsObj = bikeDetailService.getBikeHistory(authDTO, fromDate, toDate);
			for (BikeDetailsDTO bikeDetailsDto : bikeDetailsObj) {
				BikeDetailsIO bikedetailsIO = new BikeDetailsIO();
				bikedetailsIO.setCode(bikeDetailsDto.getCode());
				CustomerIO customer = new CustomerIO();
				customer.setCode(bikeDetailsDto.getCustomer().getCode());
				bikedetailsIO.setCustomer(customer);
				bikedetailsIO.setIssueDetails(bikeDetailsDto.getIssueDetails());
				DateTime bookedAt = bikeDetailsDto.getBookedAt();
				String bookedAtObj = bookedAt.format("YYYY-MM-DD");
				bikedetailsIO.setBookedAt(bookedAtObj);
				DateTime deliveryAt = bikeDetailsDto.getDeliveryAt();
				String deliveryAtObj = deliveryAt.format("YYYY-MM-DD");
				bikedetailsIO.setDeliveryAt(deliveryAtObj);
				bikedetailsIO.setStatusId(bikeDetailsDto.getStatusId());
				bikedetailsIO.setTransactionAmount(bikeDetailsDto.getTransactionAmount());
				bikedetailsIO.setActiveFlag(bikeDetailsDto.getActiveFlag());
				bikeDetailsIo.add(bikedetailsIO);
			}
		}
		return bikeDetailsIo;
	}
	
}