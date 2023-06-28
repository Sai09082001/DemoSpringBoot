package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrdersDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.OrdersService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	@Autowired  // DI Dependence Inject
	OrdersService ordersService;
	
	@Autowired  // DI Dependence Inject
	UserService userService;
	

	@PostMapping("/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDTO<OrdersDTO> add(@RequestBody @Valid OrdersDTO ordersDTO ) {
		ordersService.create(ordersDTO);
		return ResponseDTO.<OrdersDTO>builder().status(200).data(ordersDTO).build();
	}
	
	@GetMapping("/get") // 10
	public ResponseDTO<OrdersDTO> get(@RequestParam("id") int id) {
		OrdersDTO ordersDTO = ordersService.getById(id);
		return ResponseDTO.<OrdersDTO>builder().status(200).data(ordersDTO).build();
	}
	
	@GetMapping("/search") // 10
	public ResponseDTO<List<OrdersDTO>> searchByUser(@RequestParam("id") int id) {
		List<OrdersDTO> ordersDTO = ordersService.findByUserId(id);
		return ResponseDTO.<List<OrdersDTO>>builder().status(200).data(ordersDTO).build();
	}
	
	@GetMapping("/ship") // 10
	public ResponseDTO<List<OrdersDTO>> searchByShipper(@RequestParam("id") int id) {
		List<OrdersDTO> ordersDTO = ordersService.findByShipperId(id);
		return ResponseDTO.<List<OrdersDTO>>builder().status(200).data(ordersDTO).build();
	}
	
	@GetMapping("/list")
	public ResponseDTO<List<OrdersDTO>> list() {
		List<OrdersDTO> ordersList = ordersService.getAll();
		return ResponseDTO.<List<OrdersDTO>>builder().status(200).data(ordersList).build();
	}
}
