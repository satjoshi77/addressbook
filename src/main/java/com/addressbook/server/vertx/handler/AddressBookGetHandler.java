package com.addressbook.server.vertx.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.persistence.api.Store;
import com.addressbook.server.handler.AddressBookGetHandlerBase;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;;

public class AddressBookGetHandler extends AddressBookGetHandlerBase implements Handler<RoutingContext> {

	public AddressBookGetHandler(Store store) {
		super(store);
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Map<String, Object> context = new HashMap<>();
		context.put(Constants.ACCOUNT_ID, routingContext.request().getParam(Constants.ACCOUNT_ID));
		context.put(Constants.CONTACT_ID, routingContext.request().getParam(Constants.CONTACT_ID));
		context.put(Constants.STATE, routingContext.request().getParam(Constants.STATE));
		context.put(Constants.AREA_CODE, routingContext.request().getParam(Constants.AREA_CODE));
		String errorMsg = "{\"Error\":\"Contact does not exist \"}";
		Object contact=null;
		try {
			contact=super.handle(context);
			if(contact instanceof List){
				//TODO--contact.toArray(new Contact[contact.size()])
			}
		} catch (AddressBookException e) {
			// TODO log
			errorMsg = "{\"Error\":\"" + e.getMessage() + " \"}";
		}
		if (contact != null) {
			// send response
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(contact));
		} else {
			routingContext.response().setStatusCode(400)
					.putHeader(Constants.HEADER_CONTENT_TYPE, Constants.APPLICATION_JSON).end(errorMsg);
		}

	}

}
