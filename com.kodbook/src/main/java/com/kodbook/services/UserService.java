package com.kodbook.services;

import com.kodbook.entities.User;

public interface UserService {

	boolean userExists(String username, String email);

	void addUser(User user);

	boolean validateUser(String username, String password);

	User getUser(String username);

	void updateUser(User user);

}
