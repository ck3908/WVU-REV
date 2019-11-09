package carDAOs;

import entities.Users;

public interface UserDAO {
	
	Users getUserByName (String name);
	Users validateUser(String name, String passToValidate, String passStored, String code);
	
}
