package com.example.demo.service;


import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductsDTO;
import com.example.demo.entity.Products;
import com.example.demo.repository.ProductsRepo;


public interface ProductsService {

	void create(ProductsDTO productsDTO);

	void update(ProductsDTO productsDTO);
	
	List<ProductsDTO> getAll();

	void delete(int id);

	ProductsDTO getById(int id);

}

@Service
class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepo productsRepo;


	@Override
	@Transactional
	public void create(ProductsDTO productsDTO) {
		Products products = new ModelMapper().map(productsDTO, Products.class);
		productsRepo.save(products);
	}

	@Override
	public void update(ProductsDTO productsDTO) {
		// check
		Products products = productsRepo.findById(productsDTO.getId()).orElseThrow(NoResultException::new);
		
		products.setName(productsDTO.getName());
			// save entity
		productsRepo.save(products);
	

	}
	
	@Override
	public void delete(int id) {
		productsRepo.deleteById(id);
	}

	@Override
	public ProductsDTO getById(int id) {
		// Optional
		Products products = productsRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(products != null)
			return convert(products);
		
		return null;
	}
	
	private ProductsDTO convert(Products products) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(products, ProductsDTO.class);
	}

	@Override
	public List<ProductsDTO> getAll() {
		// TODO Auto-generated method stub
		List<Products> productsList = productsRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		// java 8
		return productsList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}