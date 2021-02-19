package com.jfinal.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mj.core.CoreEnum;

/**
 * 
 * @author Administrator
 * CD借阅状态：1-借阅中，2-可借阅，3-逾期
 */
@SuppressWarnings("serial")
public class CdBorrowEnum implements Serializable{
	public static final CoreEnum BORROWING = new CoreEnum(1, "BORROWING", "借阅中");
	public static final CoreEnum LOANABLE = new CoreEnum(2, "LOANABLE", "可借阅");
	public static final CoreEnum OVERDUE = new CoreEnum(3, "OVERDUE", "逾期");
	
	public static List<CoreEnum> list = list();

	public static CoreEnum valueOf(Integer ordinal) {
		if (ordinal == null) {
			return null;
		}
		switch (ordinal) {
		case 1:
			return BORROWING;
		case 2:
			return LOANABLE;
		case 3:
			return OVERDUE;
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
			return (String)BORROWING.getValue();
		case 2:
			return (String)LOANABLE.getValue();
		case 3:
			return (String)OVERDUE.getValue();
		default:
			return StringUtils.EMPTY;
		}
	}

	//--------------------------------------内部方法---------------------------------
	private static List<CoreEnum> list() {
		if(list == null){
			list = new ArrayList<CoreEnum>();
			list.add(BORROWING);
			list.add(LOANABLE);
			list.add(OVERDUE);
		}
		return list;
	}
}
