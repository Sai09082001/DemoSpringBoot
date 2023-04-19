package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Inventory;
import com.example.demo.service.InventoryService;

@RestController
public class InventoryController {
	@Autowired  // DI Dependence Inject
	InventoryService inventoryService;
	
	@GetMapping("/inventory/intro") 
	public String intro() {
		return "index.html";
	}
	
	@GetMapping("/inventory/new") 
	public String newInventory() {
		return "new-inventory.html";
	}
	
	@PostMapping("/inventory/new") 
	public String newInventory(@ModelAttribute Inventory inventory) {
		inventoryService.create(inventory);
		return "redirect:/inventory/list";
	}
	
	@GetMapping("/inventory/list")
	public List<Inventory> listInventory(Model model) { // HttpServletRequest req
		List<Inventory> inventories = inventoryService.getAll();
		// req.setAttribute("userList",users)
		model.addAttribute("inventoryList",inventories);
	//	return "inventories.html";
		return inventories;
	}
}
