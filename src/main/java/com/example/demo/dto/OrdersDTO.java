package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrdersDTO {
	private int id;
	
	private List<ProductsDTO> products;
	
	private double price;
	private String comment;

//	private DeliveryDTO delivery;
}
