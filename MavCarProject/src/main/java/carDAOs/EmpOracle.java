package carDAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import carUtils.ConnectionUtil;
import carUtils.LogUtil;
import entities.CarDetail;

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
}
