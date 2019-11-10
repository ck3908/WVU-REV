package carDAOs;



import entities.User;

public interface UserDAO {
	
	User getUserByName (String name);
	User validateUser(String name, String passToValidate);

	
}
