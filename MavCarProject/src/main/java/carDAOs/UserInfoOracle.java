package carDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

//import com.revature.data.UserOracle;
import carUtils.LogUtil;

import carUtils.ConnectionUtil;

//import com.revature.data.AddressOracle;
//import com.revature.utils.ConnectionUtil;

import entities.User;



public class UserInfoOracle implements UserDAO {
	
	private Logger log = Logger.getLogger(UserInfoOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	User getUserInfo = new User();
	

	@Override
	public User getUserByName(String name) {

		return getUserInfo;
	}; 

	@Override
	public User validateUser(String name, String passToValidate) {
		User u = null;
		log.trace("Retrieve user from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select name, password, code "
					+ "from userinfo where name=? and password = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, name);
			pstm.setString(2, passToValidate);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			if(rs.next())
			{
				log.trace("User found.");
				u = new User();
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

	@Override
	public int setNewUser(User newU) {
		
		int key = 0;
		log.trace("adding new user to db "+newU);
		log.trace(newU);
		Connection conn = cu.getConnection();
		
		try{
			conn.setAutoCommit(false);
			String sql = "insert into userinfo (name,password,code) values(?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setString(1, newU.getName());
			pstm.setString(2, newU.getPass());
			pstm.setString(3, newU.getUser_code());
			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("user created");
				key = rs.getInt(1);
				newU.setId(key);
				conn.commit();
			}
			else
			{
				log.trace("user not created.");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,UserInfoOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,UserInfoOracle.class);
			}
		}
		return key;
	}

}
