package org.in.com.service;


import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.BikeDetailsDTO;

public interface BikeDetailsService {


	public BikeDetailsDTO updateBikeDetails(AuthDTO authDTO, BikeDetailsDTO bikeDetailsObj);

	public BikeDetailsDTO getBikeDetails(AuthDTO authDTO, BikeDetailsDTO bikeDetailsObj);

	public List<BikeDetailsDTO> getBikeHistory(AuthDTO authDTO, String s, String s1);

}
