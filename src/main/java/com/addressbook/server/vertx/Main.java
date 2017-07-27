package com.addressbook.server.vertx;

import java.io.IOException;
import java.util.logging.Logger;

import com.addressbook.common.Constants;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
/**
 * NOTE:
 * Classes under package "com.addressbook.server.vertx" can be replaced by any other HTTP/REST frameworks like HttpServlet/Jersey etc. 
 * Classes in other package that contains the application/business logic can be utilized as is in other frameworks.
 * This would make migration of application to any HTTP/REST framework easy. Application is sort loose coupled with underlying 
 * HTTP container/library.
 * 
 * This the main class or the starting point for this application.
 * 
 * @author satish
 *
 */
public class Main {
	static Logger logger=Logger.getLogger(Main.class.getName());
	public static void main(String[] args) throws IOException {
		//create vert.x instance
		Vertx v= Vertx.vertx(new VertxOptions().setMaxEventLoopExecuteTime(30*VertxOptions.DEFAULT_MAX_EVENT_LOOP_EXECUTE_TIME).setMaxWorkerExecuteTime(30*VertxOptions.DEFAULT_MAX_WORKER_EXECUTE_TIME));
		//read properties
		JsonObject props = com.addressbook.common.Utils.getConfig(v);
		//deploy verticle
		v.deployVerticle(Constants.MAIN_VERTICLE_NAME,new DeploymentOptions().setConfig(props));
		logger.info("App deployment is successful!!!");
		
	}
}
