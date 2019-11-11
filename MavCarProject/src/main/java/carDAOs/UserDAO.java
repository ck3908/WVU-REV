package carDAOs;



import entities.User;

public interface UserDAO {
	
	public User getUserByName (String name);
	public User validateUser(String name, String passToValidate);
	public int setNewUser(User user);

	
}
