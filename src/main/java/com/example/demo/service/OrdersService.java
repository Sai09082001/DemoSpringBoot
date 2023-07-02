package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.OrderItemsDTO;
import com.example.demo.dto.OrdersDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;

import com.example.demo.entity.OrderItems;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Products;
import com.example.demo.entity.User;
import com.example.demo.repository.OrdersRepo;
import com.example.demo.repository.ProductsRepo;
import com.example.demo.repository.UserRepo;

public interface OrdersService {

	void create(OrdersDTO ordersDTO);

	void update(OrdersDTO ordersDTO);
	
	void updateQuantity(OrdersDTO ordersDTO);
	
	List<OrdersDTO> getAll();

	void delete(int id);

	OrdersDTO getById(int id);
	
	List<OrdersDTO> findByUserId(int userId);
	
	List<OrdersDTO> findByShipperId(int shipperId);

}

@Service
class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepo ordersRepo;
	@Autowired
	UserRepo userRepo;

	@Autowired
	ProductsRepo productRepo;

	@Transactional
	public void create(OrdersDTO ordersDTO) {
		User user = userRepo.findById(ordersDTO.getUser().getId()).orElseThrow(NoResultException::new);

		Orders orders = new Orders();
		orders.setUser(user);
		orders.setAddress(ordersDTO.getAddress());
		orders.setShopName(ordersDTO.getShopname());
		orders.setComment(ordersDTO.getComment());
		orders.setPrice(ordersDTO.getPrice());
		orders.setStates(ordersDTO.getStates());


		List<OrderItems> orderItems = new ArrayList<>();

		for (OrderItemsDTO orderItemsDTO : ordersDTO.getOrderItems()) {
			OrderItems billItem = new OrderItems();
			billItem.setOrders(orders);
			billItem.setProduct(
					productRepo.findById(orderItemsDTO.getProduct().getId()).orElseThrow(NoResultException::new));

			billItem.setPrice(orderItemsDTO.getPrice());
			billItem.setQuantity(orderItemsDTO.getQuantity());

			orderItems.add(billItem);
		}

		orders.setOrderItems(orderItems);
		ordersRepo.save(orders);
	}

	@Transactional
	public void update(OrdersDTO ordersDTO) {
		// check
		Orders currentOrders = ordersRepo.findById(ordersDTO.getId()).orElseThrow(NoResultException::new);
		currentOrders.setStates(ordersDTO.getStates());
		User shipper = new ModelMapper().map(ordersDTO.getShipper(), User.class);
		currentOrders.setShipper(shipper);
			// save entity
		ordersRepo.save(currentOrders);
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

	@Override
	public List<OrdersDTO> findByUserId(int userId) {
        List<Orders> orders = ordersRepo.findByUserId(userId);
        return orders.stream().map(u -> convert(u)).collect(Collectors.toList());
    }

	@Override
	public List<OrdersDTO> findByShipperId(int shipperId) {
		  List<Orders> orders = ordersRepo.findByShipperId(shipperId);
	       return orders.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

	@Override
	public void updateQuantity(OrdersDTO ordersDTO) {
		Orders currentOrders = ordersRepo.findById(ordersDTO.getId()).orElseThrow(NoResultException::new);
		currentOrders.setStates(ordersDTO.getStates());

		for (OrderItemsDTO orderItemsDTO : ordersDTO.getOrderItems()) {
		    Products products = productRepo.findById(orderItemsDTO.getProduct().getId()).orElseThrow(NoResultException::new);
		    int currentProducts = orderItemsDTO.getQuantity();
		    int newQuantity = products.getQuantity()-currentProducts;
		    if (newQuantity >= 0) {
		    	products.setQuantity(newQuantity);
		    }
		    productRepo.save(products);
			
		}
			// save entity
		ordersRepo.save(currentOrders);
	}

//	public PageDTO<OrderStatisticDTO> statistic() {
//		List<Object[]> list = billRepo.thongKeBill();
//
//		PageDTO<BillStatisticDTO> pageDTO = new PageDTO<>();
//		pageDTO.setTotalPages(1);
//		pageDTO.setTotalElements(list.size());
//
//		List<BillStatisticDTO> billStatisticDTOs = new ArrayList<>();
//
//		for (Object[] arr : list) {
//			BillStatisticDTO billStatisticDTO = new BillStatisticDTO((long) (arr[0]),
//					String.valueOf(arr[1]) + "/" + String.valueOf(arr[2]));
//
//			billStatisticDTOs.add(billStatisticDTO);
//		}
//
//		pageDTO.setContents(billStatisticDTOs);
//
//		return pageDTO;
//	}
}