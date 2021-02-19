package com.jfinal.kit;

import com.jfinal.kit.PropKit;
import com.mj.constant.CoreConstant;

/**
 * 环境工具类
 * 
 * @author frank
 * 		
 */
public class EnvKit {
	
	public static String get() {
		boolean devMode = PropKit.getBoolean("devMode",true);
		boolean prodTest = PropKit.getBoolean("prodTest", false);
		return devMode ? CoreConstant.ENV_BETA : (prodTest ? CoreConstant.ENV_PROD_TEST : CoreConstant.ENV_PROD);
	}
}
