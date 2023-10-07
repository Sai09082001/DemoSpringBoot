package com.example.demo.controller;

import java.io.IOException;
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

import com.example.demo.dto.ProductsDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ShopDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.ShopService;

@RestController
@RequestMapping("/shop")
public class ShopController {
	@Autowired  // DI Dependence Inject
	ShopService shopService;
	

	@PostMapping("/new")
	public ResponseDTO<Void> newProducts(@RequestBody ShopDTO shopDTO) throws IllegalStateException,IOException {
		
		shopService.create(shopDTO);
		//return "redirect:/user/list";// GET
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/") // dung thu vien convert jackson
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDTO<ShopDTO> getShopById(@RequestParam("id") int id) throws Exception {
		
		return ResponseDTO.<ShopDTO>builder().status(200).data(shopService.getById(id)).build();
	//	return ResponseEntity.ok().header("id", "1").body(departmentDTO);
	}

	@GetMapping("/{id}") // 10
	public ResponseDTO<ShopDTO> get(@PathVariable("id") int id) {
		ShopDTO shopDTO = shopService.getById(id);
		return ResponseDTO.<ShopDTO>builder().status(200).data(shopDTO).build();
	}
	
	@PostMapping("/edit")
	public ResponseDTO<Void> edit(@RequestBody @Valid ShopDTO shopDTO) throws Exception {
	
		shopService.update(shopDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/list")
	public ResponseDTO<List<ShopDTO>> list(Model model) {
		List<ShopDTO> productsList = shopService.getAll();
		return ResponseDTO.<List<ShopDTO>>builder().status(200).data(productsList).build();
	}
}
