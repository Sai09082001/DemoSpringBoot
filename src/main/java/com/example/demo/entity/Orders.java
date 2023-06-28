package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Table(name = "orders")
@Entity
public class Orders extends TimeAuditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<OrderItems> orderItems;
	
	private double price;
	private String comment;
	
	private String shopName;
	
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	 @ManyToOne
	 @JoinColumn(name = "shipper_id")
	 private User shipper;
	
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;
	
	@ElementCollection    // ap dung voi bang chi voi 2 cot , 1 cot FK
	@CollectionTable(name = "status",joinColumns = @JoinColumn(name = "delivery_id"))
	@Column(name="state")
	private List<String> states;
}
