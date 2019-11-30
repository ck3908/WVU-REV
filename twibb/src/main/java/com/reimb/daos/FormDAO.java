package com.reimb.daos;

import java.util.List;

import com.reimb.entities.Attachments;
import com.reimb.entities.FormAppr;
import com.reimb.entities.FormInfo;
import com.reimb.entities.FormRej;
import com.reimb.entities.FormReview;
import com.reimb.entities.FormStatus;
import com.reimb.entities.ReqFC;

public interface FormDAO {
	public int submitF(FormInfo formSub);
	public String getGradeReq(Integer num);
	//public int setDept(Integer deptNum, String deptName, Integer deptHead);
	
	public int insFormStatus(FormStatus fStat);
	public int getFormStatus(Integer empId, Integer formId);
	public int updateFormStatus(FormStatus fStat);
	
	public int insFormAppr(FormAppr fappr);
//	public int updateFormAppr(Integer formId, Integer empId);
	public FormAppr getFormApprov(Integer formId, Integer empId);
	
	public int insFormRej(FormRej fRej);
	public FormRej getFormRej(Integer rejId, Integer formId);
//	public int updateFormRej(Integer rejId, Integer formId);
	
	public int insFormRFC(ReqFC fRFC);
	public ReqFC getFormRFC(Integer askerId, Integer formId);
	public int updateFormRFC(ReqFC fRFC);
	
	public int insAttach(Attachments atch);
	public Attachments getAttach(Integer empId, Integer formId);
//	public int updateAttach(Integer empId, Integer formId);
	
	public int insFormRev(FormReview fRev);
	public FormReview getReview(Integer revId, Integer formId);
	public List<FormReview> getAllrevs(Integer revId);
//	public int updateReview(Integer revId, Integer formId);
	
	

}
