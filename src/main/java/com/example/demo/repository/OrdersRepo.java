package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Orders;


public interface OrdersRepo extends JpaRepository<Orders, Integer> {
	
	@Query("SELECT d FROM Orders d WHERE d.user.name LIKE :x ")
	Page<Orders> searchByOrdersName(@Param("x") String name, Pageable pageable);

}