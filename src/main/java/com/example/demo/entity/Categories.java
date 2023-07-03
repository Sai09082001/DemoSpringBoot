package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

/*
 * Doi tuong entity mo ta danh muc san pham
 */
@Data
@Table(name = "categories")
@Entity
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	private String name;
	private String categoryUrl;
	
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	@OneToMany(mappedBy = "categories" , fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Products> products;
	
	public void removeProduct(Products product) {
	    if (products != null) {
	        products.remove(product);
	        product.setCategories(null);
	    }
	}
}
