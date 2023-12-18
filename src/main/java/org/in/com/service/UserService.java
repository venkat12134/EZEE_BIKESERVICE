package org.in.com.service;


import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;

public interface UserService {

    public UserDTO updateUser(AuthDTO authDTO, UserDTO userObj);
	public List<UserDTO> getCustomerHistory(AuthDTO authDTO) ;
	public UserDTO getCustomerByCode(AuthDTO authDTO, UserDTO code);
	public UserDTO getUser(AuthDTO authDTO, UserDTO userLogin ) ;
	public AuthDTO authCreate(AuthDTO authDTO, UserDTO UserLogin) ;	
    public UserDTO getUserByToken(AuthDTO authDTO, String authtoken);
}
