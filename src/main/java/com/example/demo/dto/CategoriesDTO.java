package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Shop;

import lombok.Data;

@Data
public class CategoriesDTO {
	private int id;
	private String name;
	private String categoryUrl;
	
//	private Shop shop;
	
	private List<ProductsDTO> products;
}
