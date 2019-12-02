package com.reimb.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.reimb.daos.FormDAO;
import com.reimb.daos.FormOracle;
import com.reimb.entities.FormAppr;
import com.reimb.entities.FormInfo;
import com.reimb.entities.FormRej;
import com.reimb.entities.FormReview;
import com.reimb.entities.FormStatus;
import com.reimb.entities.ReqFC;

public class FormServiceImp implements FormService{
	private Logger log = Logger.getLogger(FormServiceImp.class);
	private FormDAO fd = new FormOracle();

	@Override
	public int submitForm(FormInfo formSub) {
		// TODO Auto-generated method stub
		int res = fd.submitF(formSub);
		return res;
	}

	@Override
	public int insertFrev(FormReview fRev) {
		// TODO Auto-generated method stub
		int res = fd.insFormRev(fRev);
		return res;
	}

	@Override
	public int insFStatus(FormStatus fStat) {
		// TODO Auto-generated method stub
		int res = fd.insFormStatus(fStat);
		return res;
	}

	@Override
	public List<FormReview> getRevs(Integer revId) {
		// TODO Auto-generated method stub
		return fd.getAllrevs(revId);
	}

	@Override
	public List<FormInfo> getFormsR(Integer revId) {
		// TODO Auto-generated method stub
		return fd.getFmtoRev(revId);
	}

	@Override
	public int getStatInfo(Integer fid) {
		// TODO Auto-generated method stub
		int res = fd.getFormStatbyFID(fid);
		return res;
	}

	@Override
	public int updFormStat(FormStatus fStat) {
		// TODO Auto-generated method stub
		int res = fd.updateFormStatus(fStat);
		return res;
	}

	@Override
	public int insFormRej(FormRej fRej) {
		// TODO Auto-generated method stub
		int res = fd.insFormRej(fRej);
		return res;
	}

	@Override
	public int insertFormRFC(ReqFC fRFC) {
		// TODO Auto-generated method stub
		int res = fd.insFormRFC(fRFC);
		return res;
	}

	@Override
	public int insertFormAppr(FormAppr fAppr) {
		// TODO Auto-generated method stub
		int res = fd.insFormAppr(fAppr);
		return res;
	}



}
