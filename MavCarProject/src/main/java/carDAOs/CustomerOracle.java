package carDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import carUtils.ConnectionUtil;
import carUtils.LogUtil;
import entities.CarDetail;
import entities.User;

public class CustomerOracle implements CustomerDAO {
	private Logger log = Logger.getLogger(UserInfoOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public List<CarDetail> viewMyCars(String username) {
		List<CarDetail> myCars = new ArrayList<CarDetail>();
		// TODO Auto-generated method stub
		log.trace("Retrieve my cars from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select * from cardetails where plate in (select carplate from usercars where username = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("availabe cars found.");
				CarDetail c = new CarDetail();
				c.setCarName(rs.getString("carname"));
				c.setPlate((rs.getInt("plate")));
				//c.setSelling_price((rs.getFloat("offersold")));
				myCars.add(c);  //add into list of cars
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, UserInfoOracle.class);
		}
		
		return myCars; //return all cars available for viewing
		
	}
	
	@Override
	public List<CarDetail> viewCarLot(){
		List<CarDetail> carsAvail = new ArrayList<CarDetail>();
		
		log.trace("Retrieve available cars from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select carname, plate, offersold from cardetails where owned = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, "false");
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			int count = 0;
			while (rs.next())
			{
				log.trace("availabe cars found.");
				CarDetail c = new CarDetail();
				c.setCarName(rs.getString("carname"));
				c.setPlate((rs.getInt("plate")));
				c.setSelling_price((rs.getFloat("offersold")));
				carsAvail.add(c);  //add into list of cars
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, UserInfoOracle.class);
		}
		
		return carsAvail; //return all cars available for viewing
		
	}


}
