package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
   @GetMapping("/login")
   public String login() {
	  return "login.html";
   }
  @PostMapping("/login")
  public String login(HttpSession session,
		  @RequestParam("username") String username,
		  @RequestParam("password") String password) {
	  if (username.equals("admin") && password.equals("123")) {
			// neu thanh cong
			// luu tam thoi - sesion - timeout
			// session : bo nho
			session.setAttribute("username", username);
			
			// chuyen huong
			// client tu dong goi sang url moi
			return "redirect:/hello"; // GET
		} else {
			// http://localhost:8080/HelloWorld/login?e=100
			return "redirect:/login"; // GET
		} 
  }
}
