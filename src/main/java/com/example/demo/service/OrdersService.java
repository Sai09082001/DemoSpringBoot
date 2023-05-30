package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrdersDTO;
import com.example.demo.entity.Orders;
import com.example.demo.repository.OrdersRepo;

public interface OrdersService {

	void create(OrdersDTO ordersDTO);

	void update(OrdersDTO ordersDTO);
	
	List<OrdersDTO> getAll();

	void delete(int id);

	OrdersDTO getById(int id);

}

@Service
class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepo ordersRepo;


	@Override
	@Transactional
	public void create(OrdersDTO ordersDTO) {
		Orders orders = new ModelMapper().map(ordersDTO, Orders.class);
		ordersRepo.save(orders);
	}

	@Override
	public void update(OrdersDTO ordersDTO) {
		// check
		Orders orders = ordersRepo.findById(ordersDTO.getId()).orElseThrow(NoResultException::new);
		
			// save entity
		ordersRepo.save(orders);
	

	}
	
	@Override
	public void delete(int id) {
		ordersRepo.deleteById(id);
	}

	@Override
	public OrdersDTO getById(int id) {
		// Optional
		Orders orders = ordersRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(orders != null)
			return convert(orders);
		
		return null;
	}
	
	private OrdersDTO convert(Orders orders) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(orders, OrdersDTO.class);
	}

	@Override
	public List<OrdersDTO> getAll() {
		// TODO Auto-generated method stub
		List<Orders> ordersList = ordersRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		// java 8
		return ordersList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}