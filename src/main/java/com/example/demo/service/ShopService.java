package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ShopDTO;
import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import com.example.demo.repository.ShopRepo;

public interface ShopService {

	void create(ShopDTO shopDTO);

	void update(ShopDTO shopDTO);
	
	List<ShopDTO> getAll();

	void delete(int id);

	ShopDTO getById(int id);

}

@Service
class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopRepo shopRepo;


	@Override
	@Transactional
	public void create(ShopDTO shopDTO) {
		Shop shop = new ModelMapper().map(shopDTO, Shop.class);
		shopRepo.save(shop);
	}

	@Override
	public void update(ShopDTO shopDTO) {
		// check
		Shop currentShop = shopRepo.findById(shopDTO.getId()).orElseThrow(NoResultException::new);
		
		if (currentShop != null) {
			currentShop.setName(shopDTO.getName());
			currentShop.setAddress(shopDTO.getAddress());
			currentShop.setPhone(shopDTO.getPhone());
           
			shopRepo.save(currentShop);
		}

	}
	
	@Override
	public void delete(int id) {
		shopRepo.deleteById(id);
	}

	@Override
	public ShopDTO getById(int id) {
		// Optional
		Shop shop = shopRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(shop != null)
			return convert(shop);
		
		return null;
	}
	
	private ShopDTO convert(Shop shop) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(shop, ShopDTO.class);
		
	}

	@Override
	public List<ShopDTO> getAll() {
		// TODO Auto-generated method stub
		List<Shop> shopList = shopRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		// java 8
		return shopList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}