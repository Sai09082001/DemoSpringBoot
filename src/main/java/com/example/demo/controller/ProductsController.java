package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ProductsDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.ShopDTO;
import com.example.demo.dto.UserDTO;
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
	
	
	@GetMapping("/{id}") // 10
	public ResponseDTO<ProductsDTO> get(@PathVariable("id") int id) {
		ProductsDTO productsDTO = productsService.getById(id);
		return ResponseDTO.<ProductsDTO>builder().status(200).data(productsDTO).build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		productsService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("delete").build();
	}
	
	@PostMapping("/edit")
	public ResponseDTO<Void> edit(@RequestBody @Valid ProductsDTO productsDTO) throws Exception {
	
		productsService.update(productsDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/list")
	public ResponseDTO<List<ProductsDTO>> list(Model model) {
		List<ProductsDTO> productsList = productsService.getAll();
		return ResponseDTO.<List<ProductsDTO>>builder().status(200).data(productsList).build();
	}
}
