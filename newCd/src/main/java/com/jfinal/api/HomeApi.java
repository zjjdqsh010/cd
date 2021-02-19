package com.jfinal.api;


import com.jfinal.enums.CdBorrowEnum;
import com.jfinal.plugin.activerecord.Db;


public class HomeApi {
	public static final HomeApi api = new HomeApi();

	/**
	 * @Description: 计算数量
	 * @author H
	 * @date 2021-02-08 14:27:40
	 * @return
	 */
	public Integer countNum() {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from base_villa t");
		return Db.queryNumber(sql.toString()).intValue();
	}
}
