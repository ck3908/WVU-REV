package carDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import carUtils.ConnectionUtil;
import carUtils.LogUtil;
import entities.CarDetail;
import entities.CarOffer;
import entities.User;

public class CustomerOracle implements CustomerDAO {
	private Logger log = Logger.getLogger(CustomerOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public List<CarDetail> viewMyCars(String username) {
		List<CarDetail> myCars = new ArrayList<CarDetail>();
		// TODO Auto-generated method stub
		log.trace("Retrieve my cars from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select * from cardetails where plate in (select carplate from ownercars where username = ?)";
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
				c.setPrinBal((rs.getFloat("principalbal")));  //set in order to see balance in one of the menu options
				myCars.add(c);  //add into list of cars
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, CustomerOracle.class);
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
			LogUtil.logException(e, CustomerOracle.class);
		}
		
		return carsAvail; //return all cars available for viewing
		
	}

	@Override
	public int enterBid(CarOffer custBid) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("adding a car bid into the database "+custBid);
		log.trace(custBid);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into caroffer (platenum,nameperson,offerprice,status,downpayment,findeal,carname) values(?,?,?,?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, custBid.getPlateNum());
			pstm.setString(2, custBid.getBuyer());
			pstm.setFloat(3, custBid.getOfferPrice());
			pstm.setString(4, custBid.getStatus());
			pstm.setFloat(5, custBid.getDownPmt());
			pstm.setInt(6, custBid.getTermFinance());
			pstm.setString(7, custBid.getVehName());			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("offer created.");
				key = rs.getInt(1);
				custBid.setId(key);
				conn.commit();
			}
			else
			{
				log.trace("author not created.");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,CustomerOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,CustomerOracle.class);
			}
		}
		
		return key;
	}

}
