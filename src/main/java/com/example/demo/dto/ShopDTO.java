package com.example.demo.dto;

import java.util.List;
import lombok.Data;

@Data
public class ShopDTO {
	private int id;
    private String name;
    private String address;
    private String phone;
    
	private List<CategoriesDTO> categories;
   
	private UserDTO user;
}
