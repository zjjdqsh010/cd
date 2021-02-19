package com.jfinal;
 

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.jfinal.api.Villa;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.controller.CdController;
import com.jfinal.controller.CdEditController;
import com.jfinal.controller.HomeController;
import com.jfinal.controller.LoginController;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.mj.druid.ConsoleSqlFilter;
import com.mj.interceptor.ErrorInterceptor;
import com.mj.interceptor.SessionInViewInterceptor;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

 
public class Start extends JFinalConfig {
 
    /**
     * 注意：用于启动的 main 方法可以在任意 java 类中创建
     * 开发项目时，建议新建一个 App.java 或者 Start.java 这样的专用 启动入口类放置用于启动的 main 方法
     */
 
    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/", 5);
    }
 
    public void configConstant(Constants me) {
    	me.setDevMode(true);
		//设置json 工厂
		me.setJsonFactory(new FastJsonFactory());
		//设置显示层渲染语言
		me.setViewType(ViewType.FREE_MARKER);
    }
 
    public void configRoute(Routes me) {
		me.setBaseViewPath("WEB-INF/pages");
		me.add("/", LoginController.class,"");
		me.add("/login", LoginController.class,"");
		me.add("/home", HomeController.class,"");
        me.add("/cd", CdController.class,"");
        me.add("/edit", CdEditController.class,"");
    }
 
    public void configEngine(Engine me) {
 
    }
 
    public void configPlugin(Plugins me) {
    	// 配置c3p0数据库连接池插件
		Prop prop = PropKit.use("jdbc-api.properties");
		DruidPlugin druidPlugin = new DruidPlugin(prop.get("jdbcUrl"), prop.get("user"),
				StringUtils.trim(prop.get("password")),StringUtils.trim(prop.get("jdbcDriver")));

		druidPlugin.addFilter(new ConsoleSqlFilter());
		if(JFinal.me().getConstants().getDevMode()) {
			// 初始连接池大小、最小空闲连接数、最大活跃连接数
			//private int initialSize = 10;
			//private int minIdle = 10;
			//private int maxActive = 100;
			druidPlugin.set(1,1, 1);
		}
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		// 设置事务级别(默认事务级别是 2,如果有从数据库读取的操作也在事务中,需要将事务级别至少提升到 4)
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin, 2);
        // 显示SQL语句
        arp.setShowSql(true);
		arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
//				//暂时
//				arp.addSqlTemplate("all.sql");
//				if(JFinal.me().getConstants().getDevMode()) {
//					arp.setShowSql(true);
//				}else {
//					arp.setShowSql(false);
//				}
				//进入
		arp.addMapping("base_villa", "id", Villa.class);
		me.add(arp);
    }
 
    public void configInterceptor(Interceptors me) {

		// 集中处理错误异常
		me.addGlobalActionInterceptor(new ErrorInterceptor());
		me.addGlobalActionInterceptor(new SessionInViewInterceptor());
    }
 
    public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctx"));
		me.add(new UrlSkipHandler(".js", false));
		//me.add(new UrlSkipHandler("/|/ca/.*|/se/.*|.*.htm|.*.html|.*.js|.*.css|.*.json|.*.png|.*.gif|.*.jpg|.*.jpeg|.*.bmp|.*.ico|.*.exe|.*.txt|.*.zip|.*.rar|.*.7z|.*.tff|.*.woff|.*.ttf|.*.map|.*.xml|.*.woff2|.*.xlsx", false));

		//webservice 路由
		me.add(new UrlSkipHandler(".*/services.*",false));
    }
    
    /**
	 * Call back after JFinal start
	 */
	@Override
	public void onStart() {
		super.onStart();
		Map<String, Object> global = new HashMap<String, Object>();
		global.put("dev_mode", JFinal.me().getConstants().getDevMode());
		global.put("assets_min", JFinal.me().getConstants().getDevMode() ? "" : ".min");

		try {
			Configuration conf = FreeMarkerRender.getConfiguration();
			conf.setSharedVariable("global", global);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}

	}
 
}