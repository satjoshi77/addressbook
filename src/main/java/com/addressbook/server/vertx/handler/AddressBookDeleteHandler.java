package com.addressbook.server.vertx.handler;

import java.util.HashMap;
import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;
import com.addressbook.server.handler.AddressBookDeleteHandlerBase;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class AddressBookDeleteHandler extends AddressBookDeleteHandlerBase implements Handler<RoutingContext> {
	public AddressBookDeleteHandler(Store store) {
		super(store);
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Map<String, Object> context = new HashMap<>();
		context.put(Constants.ACCOUNT_ID, routingContext.request().getParam(Constants.ACCOUNT_ID));
		context.put(Constants.CONTACT_ID, routingContext.request().getParam(Constants.CONTACT_ID));
		String errorMsg = "{\"Error\":\"Contact does not exist \"}";
		Contact existingContact = null;
		try {
			existingContact = (Contact)super.handle(context);
		} catch (AddressBookException e) {
			// TODO Auto-generated catch block
			errorMsg = "{\"Error\":\"" + e.getMessage() + " \"}";
		}
		if (existingContact != null) {
			// send response
			routingContext.response().setStatusCode(204).putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(existingContact));
		} else {
			routingContext.response().setStatusCode(400)
					.putHeader(Constants.HEADER_CONTENT_TYPE, Constants.APPLICATION_JSON).end(errorMsg);
		}

	}

}
