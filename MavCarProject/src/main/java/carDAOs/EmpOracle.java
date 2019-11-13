package carDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.data.CustomerOracle;

import carUtils.ConnectionUtil;
import carUtils.LogUtil;
import entities.CarDetail;
import entities.CarOffer;

public class EmpOracle implements EmpDAO {
	private Logger log = Logger.getLogger(EmpOracle.class);
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public int addToLot(CarDetail nCar) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("adding a car into the database "+nCar);
		log.trace(nCar);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into cardetails (plate,carname,owned,offersold,downpayment,totalpaid,loantype,monthlydue,loanterm,principalbal) values(?,?,?,?,?,?,?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, nCar.getPlate());
			pstm.setString(2, nCar.getCarName());
			pstm.setString(3, nCar.getOwned());
			pstm.setFloat(4, nCar.getSelling_price());
			pstm.setFloat(5, nCar.getDownpayment());
			pstm.setFloat(6, nCar.getTotalPayments());
			pstm.setInt(7, nCar.getFinancingDeal());
			pstm.setFloat(8, nCar.getMonthlyPmt());
			pstm.setInt(9, nCar.getTermRemaining());
			pstm.setFloat(10, nCar.getPrinBal());
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("new car successfully added to the lot");
				key = rs.getInt(1);
				nCar.setId(key);
				conn.commit();
			}
			else
			{
				log.trace("new car not added");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,EmpOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,EmpOracle.class);
			}
		}
		
		
		return key; //return success insert
	}

	@Override
	public void delLot(Integer plate) {
		// TODO Auto-generated method stub
		log.trace(plate);
		Connection conn = cu.getConnection();
		try {
			conn.setAutoCommit(false);
//			removeAuthors(conn, b.getId());  --- need to remove from car offer table too
//			removeGenres(conn, b.getId());  --- so need to implement this
			String sql = "delete from cardetails where plate = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, plate);

			int result = pstm.executeUpdate();
			if (result == 1) {
				log.trace("Car deleted.");
				conn.commit();
			} else {
				log.trace("Car not deleted.");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, EmpOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, EmpOracle.class);
			} 
		}
	}

	@Override
	public List<CarDetail> seeAllPmts() {
		List<CarDetail> allPmts = new ArrayList<CarDetail>();
		// TODO Auto-generated method stub
		log.trace("Retrieve all payments from database where customers are still paying loan.");
		try(Connection conn = cu.getConnection()){
			String sql = "select * from cardetails where owned = ? and loanterm > ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, "true");
			pstm.setInt(2, 0);  // make sure loan term is still > 0 
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("payments from customers found.");
				CarDetail c = new CarDetail();
				c.setCarName(rs.getString("carname"));
				c.setPlate((rs.getInt("plate")));
				c.setTotalPayments(rs.getFloat(("totalpaid")));
				c.setMonthlyPmt(rs.getFloat("monthlydue"));
				c.setTermRemaining(rs.getInt("loanterm"));
				c.setPrinBal((rs.getFloat("principalbal")));  //set in order to see balance in one of the menu options
				allPmts.add(c);  //add into list of cars
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmpOracle.class);
		}
		
		return allPmts; //return all cars available for viewing
	}

	@Override
	public List<CarOffer> findAllOffers() {  // extract info from cardetails table, and update to own if approved, then need to update 
		// TODO Auto-generated method stub    caroffer table if any changes to the status of the offer
		List<CarOffer> vehOffers = new ArrayList<>();
		log.trace("Retrieve all offers from database where status is pending");
		try(Connection conn = cu.getConnection()){
			String sql = "select * from caroffer where status = ?"; 
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, "pending");
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("pending offers from customers found.");
				CarOffer c = new CarOffer();
				c.setPlateNum(rs.getInt("platenum"));
				c.setBuyer(rs.getString("nameperson"));
				c.setOfferPrice(rs.getFloat("offerprice"));
				c.setStatus(rs.getString("status"));
				c.setDownPmt(rs.getFloat("downpayment"));
				c.setTermFinance(rs.getInt("findeal"));
				c.setVehName(rs.getString("carname"));			
				vehOffers.add(c);  //add into list of cars
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmpOracle.class);
		}
		
		return vehOffers; //return all cars available for viewing
	}

	@Override
	public int updateCarStatus(CarOffer cS) {
		// TODO Auto-generated method stub
		log.trace("update status of a car offer in the database "+cS);
		log.trace(cS);
		int result = 0;
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "update caroffer set status=? where platenum=? and nameperson=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, cS.getStatus());  //which should be accepted
			pstm.setInt(2, cS.getPlateNum());
			pstm.setString(3, cS.getBuyer());
			result = pstm.executeUpdate();
			
			if (result == 1) {
				log.trace("Car Status updated");
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, EmpOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, EmpOracle.class);
			}
		}

		return result;
	}

	@Override
	public int updateOwnStatus(CarDetail car) {
		// TODO Auto-generated method stub
				log.trace("update table cardetail where an owner was just accepted on his or her offer "+car);
				log.trace(car);
				int result = 0;
				Connection conn = cu.getConnection();
				try{
					conn.setAutoCommit(false);
					String sql = "update cardetails set owned=?, offersold=?, downpayment=?, totalpaid=?, loantype=?, monthlydue=?, loanterm=?, principalbal=? where plate=?";
					PreparedStatement pstm = conn.prepareStatement(sql);
					pstm.setString(1,car.getOwned());  
					pstm.setFloat(2,car.getSelling_price());
					pstm.setFloat(3, car.getDownpayment());
					pstm.setFloat(4, car.getTotalPayments());
					pstm.setInt(5, car.getFinancingDeal());
					pstm.setFloat(6, car.getMonthlyPmt());
					pstm.setInt(7, car.getTermRemaining());
					pstm.setFloat(8, car.getPrinBal());
					pstm.setInt(9, car.getPlate());
					result = pstm.executeUpdate();
					
					if (result == 1) {
						log.trace("Car Status updated in Cardetail table");
					}
				} catch (SQLException e) {
					LogUtil.rollback(e, conn, EmpOracle.class);
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						LogUtil.logException(e, EmpOracle.class);
					}
				}

				return result;
	}

	@Override
	public int rejOtherOffers(String accName, int platenum, String status) {
		log.trace("update car offer table to reject other offers after a bid accepted");
		int result = 0;
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "update caroffer set status =? where platenum =? and nameperson !=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,status);  
			pstm.setInt(2, platenum);
			pstm.setString(3,accName); 
			result = pstm.executeUpdate();
			
			if (result >= 1) {  // result returns depend on updated table results
				log.trace("other offers rejected in caroffer table");
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, EmpOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, EmpOracle.class);
			}
		}

		return result;
		
}

	@Override
	public List<CarDetail> getAllCars() {
		// TODO Auto-generated method stub
		List<CarDetail> allCars = new ArrayList<CarDetail>();
		
		log.trace("Retrieve all cars from database.");
		try(Connection conn = cu.getConnection()){
			String sql = "select * from cardetails";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("availabe cars found.");
				CarDetail c = new CarDetail();
				c.setCarName(rs.getString("carname"));
				c.setPlate(rs.getInt("plate"));
				c.setSelling_price(rs.getFloat("offersold"));
				c.setOwned(rs.getString("owned"));
				allCars.add(c);  //add into list of cars
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, EmpOracle.class);
		}
		
		return allCars; //return all cars available for viewing
	}
	
	@Override
	public int updateTableOwner(String username, int platenum) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("adding a car to the ownership table "+username);
		log.trace(username);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into ownercars (username,carplate) values(?,?)";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, username);
			pstm.setInt(2, platenum);
				
			int result = pstm.executeUpdate();
			key = result;
			if(result!=1)
			{
				log.warn("Insertion of customer failed.");
				conn.rollback();
			}
			else {
				log.trace("Successful insertion of customer");
				conn.commit();
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















