package com.example.demo.dto;

import lombok.Data;

@Data
public class ProductsDTO {
	private int id;
	
    private String name;
	
	private String description;
	
	private double price;
	
	private String imageUrl;
	
	private int quantity;

	private CategoriesDTO category;
}
