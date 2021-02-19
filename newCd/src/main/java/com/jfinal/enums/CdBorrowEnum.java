package com.jfinal.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mj.core.CoreEnum;

/**
 * 
 * @author Administrator
 * CD借阅状态：1-借阅中，2-未借阅
 */
@SuppressWarnings("serial")
public class CdBorrowEnum implements Serializable{
	public static final CoreEnum AUDITING = new CoreEnum(1, "AUDITING", "借阅中");
	public static final CoreEnum AUDIT_UNPASS = new CoreEnum(2, "AUDIT_UNPASS", "审核失败");
	
	public static List<CoreEnum> list = list();

	public static CoreEnum valueOf(Integer ordinal) {
		if (ordinal == null) {
			return null;
		}
		switch (ordinal) {
		case 1:
			return AUDITING;
		case 2:
			return AUDIT_UNPASS;
		default:
			return null;
		}
	}

	public static String getDisp(Integer ordinal){
		if(ordinal == null) {
			return StringUtils.EMPTY;
		}
		switch (ordinal) {
		case 1:
			return (String)AUDITING.getValue();
		case 2:
			return (String)AUDIT_UNPASS.getValue();
		default:
			return StringUtils.EMPTY;
		}
	}

	//--------------------------------------内部方法---------------------------------
	private static List<CoreEnum> list() {
		if(list == null){
			list = new ArrayList<CoreEnum>();
			list.add(AUDITING);
			list.add(AUDIT_UNPASS);
		}
		return list;
	}
}
