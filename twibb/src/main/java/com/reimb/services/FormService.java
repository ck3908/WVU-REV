package com.reimb.services;

import java.util.List;

import com.reimb.entities.FormAppr;
import com.reimb.entities.FormInfo;
import com.reimb.entities.FormRej;
import com.reimb.entities.FormReview;
import com.reimb.entities.FormStatus;
import com.reimb.entities.ReqFC;

public interface FormService {
	public int submitForm(FormInfo formSub);
	public int insertFrev(FormReview fRev);
	public int insFStatus(FormStatus fStat);
	public List<FormReview> getRevs(Integer revId);
	public List<FormInfo> getFormsR(Integer revId);
	public int getStatInfo(Integer fid);
	public int updFormStat(FormStatus fStat);
	public int insFormRej(FormRej fRej);
	public int insertFormRFC(ReqFC fRFC);
	public int insertFormAppr(FormAppr fAppr);
	

}
