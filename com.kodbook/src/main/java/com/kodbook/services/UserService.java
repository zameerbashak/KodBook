package com.kodbook.services;

import java.util.List;

import com.kodbook.entities.User;

public interface UserService {

	boolean userExists(String username, String email);

	void addUser(User user);

	boolean validateUser(String username, String password);

	User getUser(String username);

	void updateUser(User user);

	User SerachUser(String username);

	List<User> fetchAllUser();

	User search(String username);

}
