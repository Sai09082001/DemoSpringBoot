package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Shop;

public interface ShopRepo extends JpaRepository<Shop, Integer> {

}
