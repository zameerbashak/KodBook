package com.kodbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodbook.entities.User;
import com.kodbook.repositories.UserRepository;
@Service
public class UserServiceImplementation implements UserService {
@Autowired
UserRepository repo;

public boolean userExists(String username, String email) {
	User user1=repo.findByEmail(email);
	User user2=repo.findByUsername(username); 
	if(user1!=null || user2!=null) {
		return true;
	}
	return false;
}

public void addUser(User user) {
	repo.save(user);
	
}

public boolean validateUser(String username, String password) {
	String dbpass=repo.findByUsername(username).getPassword();
	if(password.equals(dbpass)) {
		return true;
	}
	else {
		return false;
	}
	
}

}
