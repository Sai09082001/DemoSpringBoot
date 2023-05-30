package com.example.demo.entity;

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

import lombok.Data;

@Data
@Table(name = "orders")
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<OrderItems> orderItems;
	
	private double price;
	private String comment;
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdAt;

	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ElementCollection    // ap dung voi bang chi voi 2 cot , 1 cot FK
	@CollectionTable(name = "status",joinColumns = @JoinColumn(name = "delivery_id"))
	@Column(name="state")
	private List<String> states;
}
