package com.jfinal.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Clear;
import com.jfinal.api.LoginApi;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.RetKit;
import com.mj.constant.CoreConstant;

public class LoginController extends Controller{

	public void index() {
		render("index.html");
	}
	
	
	/**
	 * 验证码
	 */
	@Clear
	public void authImg(){
		renderCaptcha();
	}

	@Clear
	public void doLogin() {
		String userName = this.getPara("userName");
		String pwd = this.getPara("userPwd");
		String securityCode = this.getPara("securityCode");

		if(StringUtils.isEmpty(userName)){
			renderJson(RetKit.fail("用户名为空!"));
			return ;
		}
		if(StringUtils.isEmpty(pwd)){
			renderJson(RetKit.fail("密码为空!"));
			return ;
		}
		if(StringUtils.isEmpty(securityCode)){
			renderJson(RetKit.fail("验证码为空!"));
			return;
		}
		if(!validateCaptcha("securityCode")){
			renderJson(RetKit.fail("验证码输入不正确!"));
			return;
		}
		
		Ret ret = new Ret();
		Integer sum = LoginApi.api.doLogin(userName, pwd);
		if(sum>0) {
			ret.setOk();
		}
		if(!ret.isOk()){
			ret.setFail();
			this.renderJson(ret);
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> retData = (Map<String,Object>)ret.get("data");
		// 放数据至session
		this.setSessionAttr(CoreConstant.LOGIN, retData);
		if(ret.isOk()){
		}
		renderJson( ret);
	}

	
}