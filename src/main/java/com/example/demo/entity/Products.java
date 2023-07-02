package com.example.demo.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "products")
@Entity
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
   
	private String name;
	
	private String description;
	
	private double price;
	
	private int quantity;
	
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name = "categories_id")
	private Categories categories;
	
}
