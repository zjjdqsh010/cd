package com.jfinal.controller;

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
		String id = getPara("id");
		Villa entity = Villa.dao.findById(id);
		if(entity == null) {
			entity=new Villa();
		}
		setAttr("entity", entity);
		setAttr("nav", "edit");
		render("cd_edit.html");
	}
}
