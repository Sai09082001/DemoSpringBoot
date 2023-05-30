package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrdersDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	@Autowired  // DI Dependence Inject
	OrdersService ordersService;
	

	@PostMapping("/new")
	public ResponseDTO<Void> newProducts(@RequestBody OrdersDTO ordersDTO) throws IllegalStateException,IOException {
		
		ordersService.create(ordersDTO);
		//return "redirect:/user/list";// GET
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	
	@GetMapping("/list")
	public ResponseDTO<List<OrdersDTO>> list(Model model) {
		List<OrdersDTO> ordersList = ordersService.getAll();
		return ResponseDTO.<List<OrdersDTO>>builder().status(200).data(ordersList).build();
	}
}
