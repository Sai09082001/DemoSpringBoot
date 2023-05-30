package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductsDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.Products;
import com.example.demo.service.ProductsService;

@RestController
@RequestMapping("/products")
public class ProductsController {
	@Autowired  // DI Dependence Inject
	ProductsService productsService;
	

	@PostMapping("/new")
	public ResponseDTO<Void> newProducts(@RequestBody ProductsDTO productsDTO) throws IllegalStateException,IOException {
		
		productsService.create(productsDTO);
		//return "redirect:/user/list";// GET
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	
	@GetMapping("/list")
	public ResponseDTO<List<ProductsDTO>> list(Model model) {
		List<ProductsDTO> productsList = productsService.getAll();
		return ResponseDTO.<List<ProductsDTO>>builder().status(200).data(productsList).build();
	}
}
