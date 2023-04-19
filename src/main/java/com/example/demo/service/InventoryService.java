package com.example.demo.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Inventory;
import com.example.demo.repository.InventoryRepo;

//@Component
@Service // tao bean new Service qly SpringContainer
public class InventoryService {
  
	@Autowired
	InventoryRepo inventoryRepo;
	
	@Transactional
   public void create(Inventory inventory) {
		inventoryRepo.save(inventory);
   }
   public List<Inventory> getAll() {
	   return inventoryRepo.findAll();
   }
}
