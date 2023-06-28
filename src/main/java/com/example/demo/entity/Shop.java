package com.example.demo.entity;

import java.util.Date;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "shop")
@Entity
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
    private String name;
    private String address;
    private String phone;
    
	@OneToMany(mappedBy = "shop" , fetch = FetchType.EAGER)
	private List<Categories> categories;
    
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "shop")
    private List<Orders> orders;
}
