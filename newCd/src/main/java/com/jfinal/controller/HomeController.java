package com.jfinal.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.api.HomeApi;
import com.jfinal.core.Controller;
import com.jfinal.kit.RetKit;
import com.jfinal.plugin.activerecord.Record;

public class HomeController extends Controller{

	/**
	 * @Description: 主页
	 * @author H
	 * @date 2021-02-08 13:59:14
	 * @return
	 */
	public void index() {
		setAttr("nav", "home");
		render("home_maintain.html");
	}
	
	/**
	 * @Description: 提示多少条借阅信息
	 * @author H
	 * @date 2021-02-08 14:24:45
	 * @return
	 */
	public void warning() {
		List<Record> warningList = new ArrayList<Record>();

		Integer num = HomeApi.api.countNum();
		if (num > 0) {
			Record record = new Record();
			record.set("disp", MessageFormat.format("有 {0} 条借阅信息，请尽快确认！", num));
			record.set("css", "text-primary");
			record.set("url", "cd");
			warningList.add(record);
		}

		renderJson(RetKit.ok(warningList));
	}
}
