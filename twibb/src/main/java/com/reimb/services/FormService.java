package com.reimb.services;

import java.util.List;

import com.reimb.entities.FormInfo;
import com.reimb.entities.FormReview;
import com.reimb.entities.FormStatus;

public interface FormService {
	public int submitForm(FormInfo formSub);
	public int insertFrev(FormReview fRev);
	public int insFStatus(FormStatus fStat);
	public List<FormReview> getRevs(Integer revId);

}
