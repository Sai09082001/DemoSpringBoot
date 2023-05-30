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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Table(name = "user")
@Entity
public class User {
   //map
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	private int age;
	private String name;
	private String avatarUrl;
	@Column(unique = true,name="username")
	private String username;
	private String password;
	private String phonenumber;
	// home_address in sql table
	@Column(name="home_address")
	private String homeAddress;
	
	@Column(name="email")
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date birthdate;

	@ElementCollection  // ap dung voi bang chi voi 2 cot , 1 cot FK
	@CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
	@Column(name="role")
	private List<String> roles;

	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
	private List<Shop> shops;
	
	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
	private List<Orders> orders ;
}