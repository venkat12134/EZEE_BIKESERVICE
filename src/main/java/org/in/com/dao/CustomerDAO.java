package org.in.com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class CustomerDAO {

	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO) {
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			int pindex = 0;
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_CUSTOMER_IUD(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setString(++pindex, customerDTO.getCode());
			callableStatement.setInt(++pindex, authDTO.getNamespace().getId());
			callableStatement.setString(++pindex, customerDTO.getBikeNumber());
			callableStatement.setString(++pindex, customerDTO.getAddress());
			callableStatement.setString(++pindex, customerDTO.getBikeModel());
			callableStatement.setString(++pindex, customerDTO.getFirstName());
			callableStatement.setString(++pindex, customerDTO.getLastName());
			callableStatement.setLong(++pindex, customerDTO.getMobileNumber());
			callableStatement.setString(++pindex, customerDTO.getEmailId());
			callableStatement.setInt(++pindex, customerDTO.getActiveFlag());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerDTO;
	}

	public List<CustomerDTO> getCustomerHistory(AuthDTO authDTO) {
		List<CustomerDTO> list = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, bike_number, address, bike_model, first_name, last_name, mobile_number, email_id, active_flag FROM customer WHERE namespace_id = ? AND active_flag < 2";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getUser().getNamespace().getId());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				CustomerDTO customerDto = new CustomerDTO();
				customerDto.setCode(resultSet.getString("code"));
				customerDto.setBikeNumber(resultSet.getString("bike_number"));
				customerDto.setAddress(resultSet.getString("address"));
				customerDto.setBikeModel(resultSet.getString("bike_model"));
				customerDto.setFirstName(resultSet.getString("first_name"));
				customerDto.setLastName(resultSet.getString("last_name"));
				customerDto.setMobileNumber(resultSet.getLong("Mobile_number"));
				customerDto.setEmailId(resultSet.getString("email_id"));
				customerDto.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(customerDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public CustomerDTO getCustomer(AuthDTO authDTO, CustomerDTO customer) {
		CustomerDTO customerObj = new CustomerDTO();
		try  {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, code, bike_number, address, bike_number, first_name, last_name, mobile_number, email_id, active_flag FROM customer WHERE namespace_id = ? AND code = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getUser().getNamespace().getId());
			preparedStatement.setString(2, customer.getCode());
			ResultSet resultSet = preparedStatement.executeQuery();
			{
				if (resultSet.next()) {
					customerObj.setId(resultSet.getInt("id"));
					customerObj.setCode(resultSet.getString("code"));
					customerObj.setBikeNumber(resultSet.getString("bike_number"));
					customerObj.setAddress(resultSet.getString("address"));
					customerObj.setBikeModel(resultSet.getString("bike_number"));
					customerObj.setFirstName(resultSet.getString("first_name"));
					customerObj.setLastName(resultSet.getString("last_name"));
					customerObj.setMobileNumber(resultSet.getLong("mobile_number"));
					customerObj.setEmailId(resultSet.getString("email_id"));
					customerObj.setActiveFlag(resultSet.getInt("active_flag"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerObj;
	}

	public CustomerDTO getCustomerById(AuthDTO authDTO, CustomerDTO customer) {
		CustomerDTO customerObj = new CustomerDTO();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, code, bike_number, address, bike_number, first_name, last_name, mobile_number, email_id, active_flag FROM customer WHERE namespace_id = ? AND id = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getUser().getNamespace().getId());
			preparedStatement.setInt(2, customer.getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			{
				if (resultSet.next()) {
					customerObj.setId(resultSet.getInt("id"));
					customerObj.setCode(resultSet.getString("code"));
					customerObj.setBikeNumber(resultSet.getString("bike_number"));
					customerObj.setAddress(resultSet.getString("address"));
					customerObj.setBikeModel(resultSet.getString("bike_number"));
					customerObj.setFirstName(resultSet.getString("first_name"));
					customerObj.setLastName(resultSet.getString("last_name"));
					customerObj.setMobileNumber(resultSet.getLong("mobile_number"));
					customerObj.setEmailId(resultSet.getString("email_id"));
					customerObj.setActiveFlag(resultSet.getInt("active_flag"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerObj;
	}

	public List<CustomerDTO> getCustomerByIdList(AuthDTO authDTO, List<Integer> customerDTO) {
		List<CustomerDTO> list = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT id, code, bike_number, address, bike_model, first_name, last_name, mobile_number, email_id, active_flag FROM customer WHERE namespace_id = ? AND  id = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getUser().getNamespace().getId());
			preparedStatement.setInt(2, customerDTO.get(0));
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				CustomerDTO customerDto = new CustomerDTO();
				customerDto.setId(resultSet.getInt("id"));
				customerDto.setCode(resultSet.getString("code"));
				customerDto.setBikeNumber(resultSet.getString("bike_number"));
				customerDto.setAddress(resultSet.getString("address"));
				customerDto.setBikeModel(resultSet.getString("bike_model"));
				customerDto.setFirstName(resultSet.getString("first_name"));
				customerDto.setLastName(resultSet.getString("last_name"));
				customerDto.setMobileNumber(resultSet.getLong("Mobile_number"));
				customerDto.setEmailId(resultSet.getString("email_id"));
				customerDto.setActiveFlag(resultSet.getInt("active_flag"));
				list.add(customerDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}


