package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoriesDTO;
import com.example.demo.dto.ProductsDTO;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Products;
import com.example.demo.repository.CategoriesRepo;
import com.example.demo.repository.ProductsRepo;

public interface CategoriesService {

	void create(CategoriesDTO categoriesDTO);

	void update(CategoriesDTO categoriesDTO);
	
	List<CategoriesDTO> getAll();

	void delete(int id);
	
	void deleteProduct(int idC, int idP);

	CategoriesDTO getById(int id);

}

@Service
class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Autowired
	private ProductsRepo productsRepo;
	
	@Override
	@Transactional
	public void create(CategoriesDTO categoriesDTO) {
		Categories categories = new ModelMapper().map(categoriesDTO, Categories.class);
		categoriesRepo.save(categories);
	}

	@Override
	public void update(CategoriesDTO categoriesDTO) {
		// check
		Categories categories = categoriesRepo.findById(categoriesDTO.getId()).orElseThrow(NoResultException::new);
		
		for (ProductsDTO productDTO : categoriesDTO.getProducts()) {
	        // Kiểm tra và lấy sản phẩm hiện có từ ID
	        Products product = productsRepo.findById(productDTO.getId()).orElseThrow(NoResultException::new);

	        // Cập nhật thông tin của sản phẩm
	        product.setName(productDTO.getName());
	        product.setDescription(productDTO.getDescription());
	        // Cập nhật các thuộc tính khác của sản phẩm

	        product.setCategories(categories);
	        categories.getProducts().add(product);
	    }

		categories.setName(categoriesDTO.getName());
			// save entity
		categoriesRepo.save(categories);
	

	}
	
	@Override
	public void delete(int id) {
		    // Xóa danh mục
		  categoriesRepo.deleteById(id);
	}

	@Override
	public CategoriesDTO getById(int id) {
		// Optional
		Categories categories = categoriesRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(categories != null)
			return convert(categories);
		
		return null;
	}
	
	private CategoriesDTO convert(Categories categories) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(categories, CategoriesDTO.class);
	}

	@Override
	public List<CategoriesDTO> getAll() {
		// TODO Auto-generated method stub
		List<Categories> categoriesList = categoriesRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		// java 8
		return categoriesList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteProduct(int idC, int idP) {
		 Categories category = categoriesRepo.findById(idC).orElseThrow(NoResultException::new);

		    Products product = productsRepo.findById(idP).orElseThrow(NoResultException::new);
		    category.removeProduct(product);
		    categoriesRepo.save(category);
		
	}

}