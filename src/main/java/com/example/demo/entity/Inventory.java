package com.example.demo.entity;


import javax.persistence.*;

import lombok.Data;


import lombok.Data;
import javax.persistence.*;

@Data
@Table(name = "db_inventory")
@Entity
public class Inventory {
   //map
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	private String name;
	private String price;
	private String decri;
	// home_address in sql table
	@Column(name="home_address")
	private String homeAddress;
}

