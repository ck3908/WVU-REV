package com.reimb.services;

import org.apache.log4j.Logger;

import com.reimb.daos.FormDAO;
import com.reimb.daos.FormOracle;
import com.reimb.entities.FormInfo;

public class FormServiceImp implements FormService{
	private Logger log = Logger.getLogger(FormServiceImp.class);
	private FormDAO fd = new FormOracle();

	@Override
	public int submitForm(FormInfo formSub) {
		// TODO Auto-generated method stub
		int res = fd.submitF(formSub);
		return res;
	}

}
