package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired //DI : dependency inject
	UserService userService;

	@PostMapping("/get") // dung thu vien convert jackson
	//@PostAuthorize
	public ResponseDTO<UserDTO> getByUserNameAndPassword(@RequestParam("username") String username,
			@RequestParam("password") String password) throws Exception {
		UserDTO userDTO = userService.findByUsernameAndPassword(username, password);
		return ResponseDTO.<UserDTO>builder().status(200).msg("ok").data(userDTO).build();
		
	//	return ResponseEntity.ok().header("id", "1").body(departmentDTO);
	}

	@PostMapping("/avatar")
	public ResponseDTO<Void> avatar(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException,IOException {
		// update avatar
		if(!userDTO.getFile().isEmpty()) {
			//ten file upload
			String filename = userDTO.getFile().getOriginalFilename();
			
			//luu lai file vao o cung may chu
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);

			//lay ten file luu xuong database
			userDTO.setAvatarUrl(filename);
		}
		userService.updateAvatar(userDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@PostMapping("/new")
	public ResponseDTO<Void> newUser(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException,IOException {
		
		userService.create(userDTO);
		//return "redirect:/user/list";// GET
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/download")
	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception {
		File file = new File("D:/"+filename);
		Files.copy(file.toPath(), resp.getOutputStream());
	}

	@GetMapping("/list")
	public ResponseDTO<List<UserDTO>> list(Model model) {
		List<UserDTO> users = userService.getAll();

		return ResponseDTO.<List<UserDTO>>builder().status(200).data(users).build();
	}
	
	@DeleteMapping("/delete")//?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		userService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("delete").build();
	}
	
	@GetMapping("/search")//copy lai user/list
	public ResponseDTO<PageDTO<List<UserDTO>>> search(
			@ModelAttribute @Valid SearchDTO searchDTO) {
		
		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);

		return ResponseDTO.<PageDTO<List<UserDTO>>>builder().status(200).msg("ok").data(pageUser).build();
	}
	
	@GetMapping("/edit")//?id=1000
	public String edit(@RequestParam("id") int id,Model model) {
		UserDTO userDTO = userService.getById(id);
		model.addAttribute("user", userDTO);
		return "edit-user.html";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()) {
			return "edit-user.html";
		}
		if(!userDTO.getFile().isEmpty()) {
			//ten file upload
			String filename = userDTO.getFile().getOriginalFilename();
			
			//luu lai file vao o cung may chu
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);

			//lay ten file luu xuong database
			userDTO.setAvatarUrl(filename);
		}
		userService.update(userDTO);
		return "redirect:/user/list";
	}
}