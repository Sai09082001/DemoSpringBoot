package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoriesDTO;
import com.example.demo.dto.ProductsDTO;
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
	
	@PostMapping("/edit")
	public ResponseDTO<Void> edit(@RequestBody @Valid CategoriesDTO categoriesDTO) throws Exception {
	
		categoriesService.update(categoriesDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@DeleteMapping("/delete/product")
	public ResponseDTO<Void> delete(@RequestParam("idC") int idC,@RequestParam("idP") int idP) {
		categoriesService.deleteProduct(idC, idP);
		return ResponseDTO.<Void>builder().status(200).msg("delete").build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		categoriesService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("delete").build();
	}
	
	@GetMapping("/{id}") // 10
	public ResponseDTO<CategoriesDTO> get(@PathVariable("id") int id) {
		CategoriesDTO categoriesDTO = categoriesService.getById(id);
		return ResponseDTO.<CategoriesDTO>builder().status(200).data(categoriesDTO).build();
	}
	
	
	@GetMapping("/list")
	public ResponseDTO<List<CategoriesDTO>> list(Model model) {
		List<CategoriesDTO> categoriesList = categoriesService.getAll();
		return ResponseDTO.<List<CategoriesDTO>>builder().status(200).data(categoriesList).build();
	}
}
