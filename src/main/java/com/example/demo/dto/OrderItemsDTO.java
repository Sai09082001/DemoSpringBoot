package com.example.demo.dto;


import com.example.demo.entity.Orders;
import com.example.demo.entity.Products;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class OrderItemsDTO {
	private Integer id;

//	@JsonIgnoreProperties("orderItems")
//	private OrdersDTO ordersDTO;
	
	private ProductsDTO product;
	
	private int quantity;
	
	private double price;
}
