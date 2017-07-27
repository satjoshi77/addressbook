package com.addressbook.server.vertx;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.addressbook.common.Constants;
import com.addressbook.persistence.api.Store;
import com.addressbook.persistence.impl.StoreFactory;
import com.addressbook.server.vertx.handler.AddressBookDeleteHandler;
import com.addressbook.server.vertx.handler.AddressBookGetHandler;
import com.addressbook.server.vertx.handler.AddressBookPostHandler;
import com.addressbook.server.vertx.handler.AddressBookPutHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * 
 * NOTE:
 * Classes under package "com.addressbook.server.vertx" can be replaced by any other HTTP/REST frameworks like HttpServlet/Jersey etc. 
 * Classes in other package that contains the application/business logic can be utilized as is in other frameworks.
 * This would make migration of application to any HTTP/REST framework easy. Application is sort loose coupled with underlying 
 * HTTP container/library.
 * 
 * This is the Main Verticle, it is a infrastructure code developed using vert.x framework.
 * vert.x supports non-blocking i/o & event based programming model. Though this application is not using much of the event based programming
 * model but it is leveraging the non-blocking i/o functionality of vert.x.
 * 
 * This class creates a HTTP server and listens for incoming requests.
 * /accounts/{accountId}/contacts resource for HTTP operations. It delegates requests to right
 * handler based on the HTTP method.
 * It also has support for default health check and default error handling.
 * Default implementation can be extended to more complex logic if needed.
 * 
 * @author satish
 *
 */
public class MainVerticle extends AbstractVerticle {
	static Logger logger = Logger.getLogger(MainVerticle.class.getName());
	private Store store=null;
	
	
	@Override
	public void start(Future<Void> startFuture) {
		initialize(startFuture);
		// Create a router object.
		Router router = Router.router(vertx);
		// Register all standard handlers
		router.route("/*").handler(BodyHandler.create());//handler for body content
		router.route("/*").failureHandler(this::handleFailure);//register failure handler
		router.get("/accounts/status").handler(this::status);//health check endpoint and register handler
		// GET Handler
		router.get(Constants.PATH_ADDRESS_BOOK).produces(Constants.APPLICATION_JSON)
				.handler(new AddressBookGetHandler(store));
		router.get(Constants.PATH_ADDRESS_BOOK+"/:contactId").produces(Constants.APPLICATION_JSON)
		.handler(new AddressBookGetHandler(store));
		// POST handler
		router.post(Constants.PATH_ADDRESS_BOOK).consumes(Constants.APPLICATION_JSON)
				.produces(Constants.APPLICATION_JSON).handler(new AddressBookPostHandler(store));
		// PUT handler
		router.put(Constants.PATH_ADDRESS_BOOK+"/:contactId").consumes(Constants.APPLICATION_JSON)
				.produces(Constants.APPLICATION_JSON).handler(new AddressBookPutHandler(store));
		// DELETE handler
		router.delete(Constants.PATH_ADDRESS_BOOK+"/:contactId").produces(Constants.APPLICATION_JSON)
				.handler(new AddressBookDeleteHandler(store));
		// Json.mapper.setSerializationInclusion(JsonInclude)
		
		// Create the HTTP server and pass the "accept" method as request
		// handler.
		vertx.createHttpServer().requestHandler(router::accept).listen(getPort(), result -> {
			if (result.succeeded()) {
				startFuture.complete();
			} else {
				startFuture.fail(result.cause());
			}
		});

	}

	/**
	 * initialization method, will be called during verticle deployment
	 * do any initialization here, that is needed by handlers or routers etc
	 * @param startFuture
	 */
	protected void initialize(Future<Void> startFuture) {
		try {
			//Using store factory create right implementation of backen store
			store = StoreFactory.create(config().getString(Constants.PERSISTENCE_TYPE,Constants.PERSISTENCE_DEFAULT_MEMORY));//default to memory
		} catch (Exception e) {
			startFuture.fail(e);
		}
		
	}

	/**
	 * health check handler method
	 * 
	 * @param ctx
	 */
	protected void status(RoutingContext ctx) {
		HttpServerResponse response = ctx.response();
		String host = null;
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// TODO log error
			host = "host";
		}
		String responseStr = "{\"status\":\"passing from " + host + "\"}";
		response.setStatusCode(200).putHeader(Constants.HEADER_CONTENT_TYPE, Constants.APPLICATION_JSON)
				.end(responseStr);
	}

	/**
	 * failure handler method
	 * @param ctx
	 */
	protected void handleFailure(RoutingContext ctx) {
		HttpServerResponse response = ctx.response();
		String responseStr = "{\"Error\":\"Internal Server Error\"}";
		response.setStatusCode(500).putHeader(Constants.HEADER_CONTENT_TYPE, Constants.APPLICATION_JSON)
				.end(responseStr);

	}

	protected Integer getPort() {
		// default port
		int port = Constants.DEFAULT_PORT;
		String portAsString = config().getString(Constants.CFG_HTTP_PORT);
		if (portAsString != null && portAsString.trim().length() > 0) {
			try {
				port = Integer.parseInt(portAsString);
			} catch (NumberFormatException e) {
				// log warning
				logger.log(Level.WARNING, "Using default port '" + port + "'. Reason:" + e.getMessage(), e);
			}
		}
		return port;
	}

}
