package com.taotao.erp.service;

import com.taotao.erp.domain.User;

public interface UserService {
	
	void addUser(User user);

	User getUserById(Long id);
	
	void addUserByMongodb(User user);
	
	User findUserByIdMongodb(Long id);
}
