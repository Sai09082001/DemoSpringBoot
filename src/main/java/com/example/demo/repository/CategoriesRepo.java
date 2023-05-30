package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Integer> {
	

}
