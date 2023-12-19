package org.in.com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.BikeDetailsDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.enumeration.BikeDetailsEnum;
import org.springframework.stereotype.Repository;

import hirondelle.date4j.DateTime;
import lombok.Cleanup;

@Repository
public class BikeDetailsDAO {

	public BikeDetailsDTO updateCustomer(AuthDTO authDTO, BikeDetailsDTO bikedetailsDTO) {
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			int index = 0;
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_BIKESERVICE_DETAILS_IUD(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setString(++index, bikedetailsDTO.getCode());
			callableStatement.setInt(++index, authDTO.getNamespace().getId());
			callableStatement.setInt(++index, bikedetailsDTO.getCustomer().getId());
			callableStatement.setString(++index, bikedetailsDTO.getIssueDetails());
			DateTime bookedAt = bikedetailsDTO.getBookedAt();
			String bookedAtObj = bookedAt.format("YYYY-MM-DD");
			callableStatement.setString(++index, bookedAtObj);
			DateTime deliveryAt = bikedetailsDTO.getDeliveryAt();
			String deliveryAtObj = deliveryAt.format("YYYY-MM-DD");
			callableStatement.setString(++index, deliveryAtObj);
			callableStatement.setString(++index, bikedetailsDTO.getStatusId().name());
			callableStatement.setInt(++index, bikedetailsDTO.getTransactionAmount());
			callableStatement.setInt(++index, bikedetailsDTO.getActiveFlag());
			callableStatement.setInt(++index, authDTO.getUser().getId());
			callableStatement.execute();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return bikedetailsDTO;
	}

	public BikeDetailsDTO getBikeDetails(AuthDTO authDTO, BikeDetailsDTO bikeDetailsDTO) {
		BikeDetailsDTO bikedetailsObj = new BikeDetailsDTO();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();

			String sql = "SELECT code, customer_id, issue_details, booked_at, delivery_at, transaction_amount, status_id, active_flag FROM bike_service_details WHERE namespace_id = ? AND code = ? AND active_flag = 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, authDTO.getNamespace().getId());
			preparedStatement.setString(2, bikeDetailsDTO.getCode());

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				bikedetailsObj.setCode(resultSet.getString("code"));
				CustomerDTO customer = new CustomerDTO();
				customer.setId(resultSet.getInt("customer_id"));
				bikedetailsObj.setCustomer(customer);
				bikedetailsObj.setIssueDetails(resultSet.getString("issue_details"));
				String bookedAt = resultSet.getString("booked_at");
				DateTime bookedAtObj = new DateTime(bookedAt);
				bikedetailsObj.setBookedAt(bookedAtObj);
				String deliveryAt = resultSet.getString("delivery_at");
				DateTime deliveryAtObj = new DateTime(deliveryAt);
				bikedetailsObj.setDeliveryAt(deliveryAtObj);
				bikedetailsObj.setTransactionAmount(resultSet.getInt("transaction_amount"));
				bikedetailsObj.setStatusId(BikeDetailsEnum.valueOf(resultSet.getString("status_id")));
				bikedetailsObj.setActiveFlag(resultSet.getInt("active_flag"));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return bikedetailsObj;

	}

	public List<BikeDetailsDTO> getBikeHistory(AuthDTO authDTO, String fromDate, String toDate) {
		List<BikeDetailsDTO> bikedetailsDto = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = ConnectionDAO.getConnection();
			String sql = "SELECT code, customer_id, issue_details, booked_at, delivery_at, transaction_amount, status_id, active_flag FROM bike_service_details WHERE booked_at BETWEEN ? AND ? AND namespace_id = ?";
			PreparedStatement preparestament = connection.prepareStatement(sql);
			preparestament.setString(1, fromDate);
			preparestament.setString(2, toDate);
			preparestament.setInt(3, authDTO.getNamespace().getId());
			ResultSet resultSet = preparestament.executeQuery();
			while (resultSet.next()) {
				BikeDetailsDTO bikedetailsObj = new BikeDetailsDTO();
				bikedetailsObj.setCode(resultSet.getString("code"));
				int customerId = resultSet.getInt("customer_id");
				CustomerDTO customer = new CustomerDTO();
				customer.setId(customerId);
				bikedetailsObj.setCustomer(customer);
				bikedetailsObj.setIssueDetails(resultSet.getString("issue_details"));
				String bookedAt = resultSet.getString("booked_at");
				DateTime obj = new DateTime(bookedAt);
				bikedetailsObj.setBookedAt(obj);
				String deliveryAt = resultSet.getString("delivery_at");
				DateTime deliveryAtObj = new DateTime(deliveryAt);
				bikedetailsObj.setDeliveryAt(deliveryAtObj);
				bikedetailsObj.setTransactionAmount(resultSet.getInt("transaction_amount"));
				BikeDetailsEnum status = BikeDetailsEnum.valueOf(resultSet.getString("status_id"));
				bikedetailsObj.setStatusId(status);
				bikedetailsObj.setActiveFlag(resultSet.getInt("active_flag"));
				bikedetailsDto.add(bikedetailsObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bikedetailsDto;
	}

}
