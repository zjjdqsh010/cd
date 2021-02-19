package com.jfinal.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.api.Villa;
import com.jfinal.core.Controller;

public class CdEditController extends Controller{
	
	/**
	 * @Description: 专辑信息编辑页面
	 * @author H
	 * @date 2021-02-08 13:59:14
	 * @return
	 */
	public void index() {
		List<String> statusList = new ArrayList<String>();
		statusList.add("古典音乐");
		statusList.add("爵士");
		statusList.add("摇滚");
		statusList.add("R&B");
		statusList.add("说唱");
		String id = getPara("id");
		Villa entity = Villa.dao.findById(id);
		if(entity == null) {
			entity=new Villa();
		}
		setAttr("statusList", statusList);
		setAttr("entity", entity);
		setAttr("nav", "edit");
		render("cd_edit.html");
	}
}
