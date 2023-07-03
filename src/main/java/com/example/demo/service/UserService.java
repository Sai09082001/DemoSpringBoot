package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;

@Service // tao bean: new Uservice, qly SpringContainer
public class UserService {

	@Autowired
	UserRepo userRepo;

	@Transactional
	public void create(UserDTO userDTO) {
		User user = new ModelMapper().map(userDTO, User.class);
		userRepo.save(user);
	}

	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);
	}

	@Transactional
	public UserDTO findByUsernameAndPassword(String username, String password) {
		User user = userRepo.findByUsernameAndPassword(username,password);
		if (user != null) return convert(user);
		else return null;
	}
	
	@Transactional
	public void update(UserDTO userDTO) {
		// check
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
			currentUser.setName(userDTO.getName());
			currentUser.setAge(userDTO.getAge());
			currentUser.setHomeAddress(userDTO.getHomeAddress());
            if (userDTO != null && userDTO.getAvatarUrl() != null) {
            	currentUser.setAvatarUrl(userDTO.getAvatarUrl());
            }
			userRepo.save(currentUser);
		}
	}
	
	@Transactional
	public void updateAvatar(UserDTO userDTO) {
		// check
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
            if (userDTO != null) {
            	currentUser.setAvatarUrl(userDTO.getAvatarUrl());
            }
			userRepo.save(currentUser);
		}
	}
	
	@Transactional
	public void updatePassword(UserDTO userDTO) {
		// check
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		if (currentUser != null) {
			currentUser.setPassword(userDTO.getPassword());

			userRepo.save(currentUser);
		}
	}

	public UserDTO getById(int id) {
		// Optional
		User user = userRepo.findById(id).orElse(null);
		if (user != null) {
			return convert(user);
		}
		return null;
	}

	private UserDTO convert(User user) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setAge(user.getAge());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setAvatarUrl(user.getAvatarUrl());
//        userDTO.setHomeAddress(user.getHomeAddress());
		return new ModelMapper().map(user, UserDTO.class);
	}

	public List<UserDTO> getAll() {
		List<User> userList = userRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		// java 8
		return userList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

	public PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending().and(Sort.by("age").descending());
		
		if(StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}
		
		if(searchDTO.getCurrentPage() == null)
			searchDTO.setCurrentPage(0);
		
		if(searchDTO.getSize() == null)
			searchDTO.setSize(5);
		
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<User> page =  userRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		PageDTO<List<UserDTO>> pageDTO = new PageDTO<List<UserDTO>>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		List<UserDTO> userDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
//		List<User> userList = userRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//	
		pageDTO.setData(userDTOs);
		return pageDTO;
	}

}