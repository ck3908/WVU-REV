package com.reimb.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.log4j.Logger;

import com.reimb.entities.Attachments;
import com.reimb.entities.FormAppr;
import com.reimb.entities.FormInfo;
import com.reimb.entities.FormRej;
import com.reimb.entities.FormReview;
import com.reimb.entities.FormStatus;
import com.reimb.entities.ReqFC;
import com.reimb.utils.ConnectionUtil;
import com.reimb.utils.LogUtil;



public class FormOracle implements FormDAO {
	private static Logger log = Logger.getLogger(EmplOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public int submitF(FormInfo formSub) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("adding new form db "+formSub);
		log.trace(formSub);
		Connection conn = cu.getConnection();
		
		try{
			conn.setAutoCommit(false);
			String sql = "insert into finfo (submitter,submitdate,location,reqamount, reimbamount, gradingfmtid) values(?,?,?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, formSub.getEmpId());
			pstm.setDate(2, (Date) formSub.getSubDate());
			pstm.setString(3, formSub.getEmpLoc());
			pstm.setInt(4, formSub.getReqAmt());
			pstm.setInt(5, formSub.getReimbAmt());
			pstm.setInt(6, formSub.getGradeFmt());
			
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("form submitted");
				key = rs.getInt(1);
				formSub.setEmpId(key);
				conn.commit();
			}
			else
			{
				log.trace("form not submitted");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,FormOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,FormOracle.class);
			}
		}	
				
		
		return key;
	}

	@Override
	public String getGradeReq(Integer num) {
		// TODO Auto-generated method stub
		log.trace("Retrieve grading requirements from database.");
		String gReq = " ";
		try(Connection conn = cu.getConnection()){
			String sql = "select gradingreq from fgrade where gradingid = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("availabe cars found.");
				gReq = rs.getString("gradingreq");
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, FormOracle.class);
		}
		
		return gReq; //return all cars available for viewing
	}

	@Override
	public int insFormStatus(FormStatus fStat) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("adding status of a form into the database "+fStat);
		log.trace(fStat);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into fstatus (submitter,formid,status) values(?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, fStat.getEmpId());
			pstm.setInt(2, fStat.getEmpId());
			pstm.setInt(3, fStat.getStatus());		
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("offer created.");
				key = rs.getInt(1);
				fStat.setId(key);
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
			LogUtil.rollback(e,conn,FormOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,FormOracle.class);
			}
		}
		
		return key;
	}

	@Override
	public int getFormStatus(Integer empId, Integer formId) {
		log.trace("Retrieve form status from database.");
		int fstatus = 0;
		try(Connection conn = cu.getConnection()){
			String sql = "select status from fstatus where submitter = ? and formid = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, empId);
			pstm.setInt(1, formId);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("status available");
				fstatus = rs.getInt("status");
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, FormOracle.class);
		}
		
		return fstatus; //return status code
	}

	@Override
	public int updateFormStatus(FormStatus fstat) {
		// TODO Auto-generated method stub
		log.trace("update form status " +fstat);
		log.trace(fstat);
		int result = 0;
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "update fstatus set status=? where submitter=? and formid=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, fstat.getStatus());
			pstm.setInt(2, fstat.getFormId());			
			pstm.setInt(3, fstat.getEmpId());  
			result = pstm.executeUpdate();
			
			if (result == 1) {
				log.trace("Car Status updated");
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, FormOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, FormOracle.class);
			}
		}

		return result;
	}

	@Override
	public int insFormAppr(FormAppr fappr) {
		int key = 0;
		log.trace("adding new approval to db "+fappr);
		log.trace(fappr);
		Connection conn = cu.getConnection();
		
		try{
			conn.setAutoCommit(false);
			String sql = "insert into fapprove (submitter,formid,approve,override) values(?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, fappr.getEmpId());
			pstm.setInt(2, fappr.getFormId());
			pstm.setDate(3, (Date) fappr.getApprovDt());
			pstm.setInt(4, fappr.getOverride());		
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("form submitted");
				key = rs.getInt(1);
				fappr.setEmpId(key);
				conn.commit();
			}
			else
			{
				log.trace("form not submitted");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,FormOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,FormOracle.class);
			}
		}				
		
		return key;
	}

//	@Override
//	public int updateFormAppr(Integer formId, Integer empId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public FormAppr getFormApprov(Integer formId, Integer empId) {
		log.trace("Retrieve form approved from database.");
		FormAppr fa = new FormAppr();
		try(Connection conn = cu.getConnection()){
			String sql = "select submitter, formid, approvedate, override from fstatus where submitter = ? and formid = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, empId);
			pstm.setInt(2, formId);
			pstm.setDate(3, (Date) fa.getApprovDt());
			pstm.setInt(4, formId);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("getting form approval object");
				fa.setEmpId(rs.getInt("submitter"));
				fa.setFormId(rs.getInt("formid"));
				fa.setApprovDt(rs.getDate("approvedate"));
				fa.setOverride(rs.getInt("override"));
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, FormOracle.class);
		}
		
		return fa; //return status code
	}

	@Override
	public int insFormRej(FormRej fRej) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("inserting form reject in the database "+fRej);
		log.trace(fRej);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into freject (formid,rejecterid,reason) values(?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, fRej.getFormId());
			pstm.setInt(2, fRej.getrejId());
			pstm.setString(3, fRej.getReason());		
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("form rej create.");
				key = rs.getInt(1);
				fRej.setId(key);
				conn.commit();
			}
			else
			{
				log.trace("form not created.");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,FormOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,FormOracle.class);
			}
		}
		
		return key;
	}

	@Override
	public FormRej getFormRej(Integer rejId, Integer formId) {
		// TODO Auto-generated method stub
		log.trace("Retrieve form approved from database.");
		FormRej fj = new FormRej();
		try(Connection conn = cu.getConnection()){
			String sql = "select formid, rejecterid, reason from freject where formid = ? and rejecterid = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rejId);
			pstm.setInt(2, formId);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("getting form rejected form object");
				fj.setFormId(rs.getInt("formid"));
				fj.setrejId(rs.getInt("rejecterid"));
				fj.setReason(rs.getString("reason"));
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, FormOracle.class);
		}
		
		return fj; //return status code
	}

//	@Override
//	public int updateFormRej(Integer empId, Integer formId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public int insFormRFC(ReqFC fRFC) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("inserting form RFC in the database "+fRFC);
		log.trace(fRFC);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into reqfc (askerid,formid,question,answer) values(?,?,?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, fRFC.getAskId());
			pstm.setInt(2, fRFC.getFormId());
			pstm.setString(3, fRFC.getQuestion());
			pstm.setString(4, fRFC.getAnswer());	
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("form request for comment create.");
				key = rs.getInt(1);
				fRFC.setId(key);
				conn.commit();
			}
			else
			{
				log.trace("form RFC not created.");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,FormOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,FormOracle.class);
			}
		}
		
		return key;
	}

	@Override
	public ReqFC getFormRFC(Integer askerId, Integer formId) {
		log.trace("Retrieve form form request for comment from database.");
		ReqFC rfc = new ReqFC();
		try(Connection conn = cu.getConnection()){
			String sql = "select question, answer from reqfc where askerid = ? and formid = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, askerId);
			pstm.setInt(2, formId);
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("getting form request from comment form object");
				rfc.setAskId(askerId);
				rfc.setFormId(formId);
				rfc.setQuestion(rs.getString("question"));
				rfc.setAnswer(rs.getString("answer"));
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, FormOracle.class);
		}
		
		return rfc; //return status code
	}

	@Override
	public int updateFormRFC(ReqFC rfc) {  //made it object input because either answer or question can be updated
		// TODO Auto-generated method stub
		log.trace("update form RFC " +rfc);
		log.trace(rfc);
		int result = 0;
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "update reqfc set question=? and answer=? where askerid=? and formid=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, rfc.getQuestion());
			pstm.setString(2, rfc.getAnswer());
			pstm.setInt(3, rfc.getAskId());
			pstm.setInt(4, rfc.getFormId());			
			result = pstm.executeUpdate();
			
			if (result == 1) {
				log.trace("Car Status updated");
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, FormOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, FormOracle.class);
			}
		}

		return result;
	}

	@Override
	public int insAttach(Attachments atch) {
		// TODO Auto-generated method stub
		int key = 0;
		log.trace("inserting attachment in the database "+atch);
		log.trace(atch);
		Connection conn = cu.getConnection();
		try{
			conn.setAutoCommit(false);
			String sql = "insert into fattach (formid,urlstring) values(?,?)";
			String[] keys = {"id"};
			PreparedStatement pstm = conn.prepareStatement(sql, keys);
			pstm.setInt(1, atch.getFormId());
			pstm.setString(2, atch.getUrlStr());
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			
			if(rs.next())
			{
				log.trace("form attachment create.");
				key = rs.getInt(1);
				atch.setId(key);
				conn.commit();
			}
			else
			{
				log.trace("form attachment not created.");
				conn.rollback();
			}
		}
		catch(SQLException e)
		{
			LogUtil.rollback(e,conn,FormOracle.class);
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e,FormOracle.class);
			}
		}
		
		return key;
	}

	@Override
	public Attachments getAttach(Integer empId, Integer formId) {
		log.trace("Retrieve attachment form from database.");
		Attachments atc = new Attachments();
		try(Connection conn = cu.getConnection()){
			String sql = "select formid, urlstring from fattach where formid = ?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, formId);
			
			ResultSet rs = pstm.executeQuery();  // this implies password matches here
			//username is unique, this query can only ever return a single result, so if is ok.
			while (rs.next())
			{
				log.trace("getting attachment form object");
				atc.setId(rs.getInt("id"));  //might as well pick up user's
				atc.setFormId(rs.getInt("formid"));
				atc.setUrlStr(rs.getString("urlstring"));
			}
		}
		catch(Exception e)
		{
			LogUtil.logException(e, FormOracle.class);
		}
		
		return atc; //return status code
	}

	@Override
	public int updateAttach(Integer empId, Integer formId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insFormRev(FormReview fRev) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FormReview getReview(Integer empId, Integer formId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateReview(Integer empId, Integer formId) {
		// TODO Auto-generated method stub
		return 0;
	}


}
