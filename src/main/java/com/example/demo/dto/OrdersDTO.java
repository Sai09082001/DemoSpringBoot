package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class OrdersDTO {
	private int id;
	
	private List<OrderItemsDTO> orderItems;
	
	private double price;
	private String shopname;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date createdAt;
	private String address;
	private String comment;
	private String states;
	private UserDTO user;
	private UserDTO shipper;
	//private ShopDTO shop;
}
