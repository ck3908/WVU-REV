package services;

import carDAOs.UserDAO;
import carDAOs.UserInfoOracle;
import entities.User;

public class UserServiceOracle implements UserService {
	private UserDAO ud = new UserInfoOracle();

	@Override
	public User findUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User checkUser(String name, String passValid) {
		// TODO Auto-generated method stub
		return ud.validateUser(name, passValid);
	}

	@Override
	public int inputUser(User nU) {   // create new user
		// TODO Auto-generated method stub
		return ud.setNewUser(nU);
	}

}
