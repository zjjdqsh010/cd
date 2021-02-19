package com.mj.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.RetKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mj.constant.CoreConstant;

/**
 * 集中处理错误异常
 * 
 * @author frank.zong
 * 		
 */
public class ErrorInterceptor implements Interceptor {

	private static String[] errIndexArr = {"Ctrl","Controller","Api"};

	@Override
	public void intercept(Invocation inv) {
		Controller ctrl = inv.getController();
		// 设置项目名称
		try {
			inv.invoke();
		} catch (Exception e) {
			LogKit.error(e.getMessage());
			//记录到数据库
			Record record = new Record();
			String err_msg = e.getMessage();
			if(StringUtils.isBlank(e.getMessage())) {
				try {
					err_msg = e.toString();
				} catch (Exception e2) {
					err_msg = "无法获取错误信息";
				}
			}

			StringBuilder errMsg = new StringBuilder();
			errMsg.append(err_msg);
			errMsg.append("<br>访问路径："+inv.getActionKey()+"?"+ctrl.getRequest().getQueryString());
			StackTraceElement[] msgPath= e.getStackTrace();
			if(msgPath!=null) {
				for (StackTraceElement stackTraceElement : msgPath) {
					for (String errIndex : errIndexArr) {
						if(stackTraceElement.getClassName().indexOf(errIndex)!=-1) {
							errMsg.append("<br>代码路径："+stackTraceElement.getClassName()+" | "+stackTraceElement.getLineNumber()+" | "+stackTraceElement.getMethodName());
						}
					}
				}
			}
			
			@SuppressWarnings("unchecked")
			Map<String,Object> dataMap =(Map<String,Object>) ctrl.getSessionAttr(CoreConstant.LOGIN);
			if(dataMap!=null) {
				errMsg.append("<br>用户名："+dataMap.get("userName"));
			}
			Map<String,String[]> map = ctrl.getRequest().getParameterMap();
			if(map!=null) {
				errMsg.append("<br>参数：");
				for(Map.Entry<String, String[]> entry : map.entrySet()){
					String mapKey = entry.getKey();
					String[] mapValue = entry.getValue();
					errMsg.append(mapKey+":");
					for (String value : mapValue) {
						errMsg.append(value+"  ");
					}
				}
			}

			String item="";
			String[] rootPath = PathKit.getWebRootPath().split("\\\\");
			for (int m = 0; m < rootPath.length;m++) {
				if(StringUtils.equals("src", rootPath[m])){
					item = rootPath[m-1];
					break;
				}
			}
			
			if(StringUtils.isBlank(item)) {
				item =  PathKit.getWebRootPath();
			}
			
			if(!JFinal.me().getConstants().getDevMode()) {
				record.set("err_msg", errMsg.toString());
				record.set("err_time", new Date());
				record.set("root_path", PathKit.getWebRootPath());
				record.set("handle_flg", "N");
				Db.save("sys_exception_log", record);
			}

			e.printStackTrace();
			boolean hasFormToken = false;
			Enumeration<String> para = ctrl.getParaNames();
			while (para.hasMoreElements()) {
				Object obj= para.nextElement();
				if(obj instanceof String){
					if(StringUtils.startsWith(obj.toString(), CoreConstant.FORM_TOKEN)){
						hasFormToken =true;
						break;
					}
				}
			}	
			if(LogKit.isErrorEnabled()){
				e.printStackTrace();
				LogKit.error("拦截器集中处理错误异常", e.fillInStackTrace());
			}
			if(ctrl.getRequest().getHeader("x-requested-with") != null 
					&&ctrl.getRequest().getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				Ret ret = RetKit.fail("系统错误，错误原因："+e.getMessage());
				if(hasFormToken){
					String formTokenSuffix = ctrl.getPara("formTokenSuffix");
					ctrl.createToken(CoreConstant.FORM_TOKEN+formTokenSuffix, 30*60);
					String token = (String)ctrl.getSessionAttr(CoreConstant.FORM_TOKEN + formTokenSuffix);
					ret.set(CoreConstant.FORM_TOKEN, token);
				}
				ctrl.renderJson(ret);
			}else{
				ctrl.setAttr("exceptionMsg", e.getMessage());
				ctrl.renderError(500);
			}
		}
	}
}
