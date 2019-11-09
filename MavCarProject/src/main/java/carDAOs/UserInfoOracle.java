package carDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

//import com.revature.data.UserOracle;
import carUtils.LogUtil;

import carUtils.ConnectionUtil;

//import com.revature.data.AddressOracle;
//import com.revature.utils.ConnectionUtil;

import entities.Users;



public class UserInfoOracle implements UserDAO {
	
	private Logger log = Logger.getLogger(UserInfoOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	Users getUserInfo = new Users();

	@Override
	public Users getUserByName(String name) {


		return getUserInfo;
	}; 

	@Override
	public Users validateUser(String name, String passToValidate, String passStored, String dbCode) {
		Users u = null;
		log.trace("Retrieve user from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select name, password, code "
					+ "from userinfo where name=? and password = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, u.getName());
			pstm.setString(2, u.getPass());
			ResultSet rs = pstm.executeQuery();
			//username is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("User found.");
				u = new Users();
				u.setName(rs.getString("name"));
				u.setPass(rs.getString("password"));
				u.setUser_code(rs.getString("code"));
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, UserInfoOracle.class);
		}
		
		return u;

	}

}
