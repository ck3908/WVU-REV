package services;

import entities.User;

public interface UserService {
	
	User findUser(String name);
	public User checkUser(String name, String passValid);
	public int inputUser(User newUser);

}
