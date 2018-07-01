package com.taotao.erp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.erp.domain.User;
import com.taotao.erp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/addUser")
	public String addUser(User user) {
		userService.addUser(user);
		return "ok";
	}

	@RequestMapping("/getUserById")
	public User getUserById(Long id) {
		return userService.getUserById(id);
	}

	@RequestMapping("/addUserByMongodb")
	public String addUserByMongodb(User user) {
		userService.addUserByMongodb(user);
		return "ok";
	}

	@RequestMapping("/findUserByIdMongodb")
	public User findUserByIdMongodb(Long id) {
		return userService.findUserByIdMongodb(id);
	}
}
