package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoriesDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ShopDTO;
import com.example.demo.service.CategoriesService;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
	@Autowired  // DI Dependence Inject
	CategoriesService categoriesService;
	

	@PostMapping("/new")
	public ResponseDTO<Void> newProducts(@RequestBody CategoriesDTO categoriesDTO) throws IllegalStateException,IOException {
		
		categoriesService.create(categoriesDTO);
		//return "redirect:/user/list";// GET
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/") // dung thu vien convert jackson
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDTO<CategoriesDTO> getCategoriesById(@RequestParam("id") int id) throws Exception {
		
		return ResponseDTO.<CategoriesDTO>builder().status(200).data(categoriesService.getById(id)).build();
	//	return ResponseEntity.ok().header("id", "1").body(departmentDTO);
	}

	
	@GetMapping("/list")
	public ResponseDTO<List<CategoriesDTO>> list(Model model) {
		List<CategoriesDTO> categoriesList = categoriesService.getAll();
		return ResponseDTO.<List<CategoriesDTO>>builder().status(200).data(categoriesList).build();
	}
}
