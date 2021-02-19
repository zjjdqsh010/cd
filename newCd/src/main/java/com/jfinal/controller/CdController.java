package com.jfinal.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.api.CdApi;
import com.jfinal.api.Villa;
import com.jfinal.core.ActionException;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.mj.constant.CoreConstant;

public class CdController extends Controller{
	
	/**
	 * @Description: cd管理页面
	 * @author H
	 * @date 2021-02-08 13:55:27
	 * @return
	 */
	public void index() {
		setAttr("nav", "maintain");
		setAttr("villa", Villa.dao.findById("5fd03b1f132b377143d78227"));
		render("cd_maintain.html");
	}
	
	public void list() {
		Integer offset = 0, pageSize = 20;
		try {
			offset = this.getParaToInt("offset", 0);
			pageSize = this.getParaToInt("limit", 10);
		} catch (ActionException e) {
			// do nothing
		}
		Integer pageNo = 1;
		if (offset > 0) {
			pageNo = (offset / pageSize) + 1;
		}
		Map<String, Object> cond = new HashMap<String, Object>(5);
		// 搜索
		cond.put(CoreConstant.SEARCH_KEYWORD, getPara("search", StringUtils.EMPTY));
		cond.put(CoreConstant.ORDER_FIELD, getPara("sort", StringUtils.EMPTY));
		cond.put(CoreConstant.ORDER_DIR, getPara("order", "asc"));


		///////////////////////////////////////////////////////////////////
		// 列表查询：每个页面都要这样写 - 固定代码快
		cond.put("pageNo", pageNo);
		cond.put("pageSize", pageSize);
		///////////////////////////////////////////////////////////////////
		Page<Record> page = com.jfinal.api.CdApi.api.page(pageNo, pageSize, cond);
		for (Record record : page.getList()) {
			record.set("floor_num_str", record.getInt("floor_num") + "层");
			record.set("selected_flg_disp", StringUtils.equals("Y", record.getStr("selected_flg")) ? "是" : "否");
		}
		renderJson(page);
	}
	
	
	/**
	 * @Description: 详情
	 * @author H
	 * @date 2020-12-08 14:09:32
	 * @return
	 */
	public void view() {
		String id = getPara("id");
		Villa entity = CdApi.api.findById(id);
		setAttr("entity", entity);
		render("cd_view.html");
	}

}
