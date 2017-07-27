package com.addressbook.server.vertx.handler;

import java.util.HashMap;
import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;
import com.addressbook.server.handler.AddressBookPostHandlerBase;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class AddressBookPostHandler extends AddressBookPostHandlerBase implements Handler<RoutingContext> {

	public AddressBookPostHandler(Store store) {
		super(store);
	}

	@Override
	public void handle(RoutingContext routingContext) {
		// TODO input validation for accountId and contactId
		Contact contact = Json.decodeValue(routingContext.getBodyAsString(), Contact.class);
		String errorMessage = null;
		if (contact != null) {
			if (contact.getId() == null) {
				errorMessage = "{\"Error\":\"Invalid contactId \"}";
			}
			Map<String, Object> context = new HashMap<>();
			context.put(Constants.ACCOUNT_ID, routingContext.request().getParam(Constants.ACCOUNT_ID));
			context.put(Constants.CONTACT_KEY, contact);
			try {
				super.handle(context);
			} catch (AddressBookException e) {
				errorMessage = "{\"Error\":\""+e.getMessage()+" \"}";
			}
		}
		if (errorMessage != null) {
			routingContext.response().setStatusCode(400)
					.putHeader(Constants.HEADER_CONTENT_TYPE, Constants.APPLICATION_JSON).end(errorMessage);
		} else {
			// send response
			routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(contact));
		}
	}

}
