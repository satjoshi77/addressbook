package com.addressbook.server.vertx.handler;

import java.util.HashMap;
import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;
import com.addressbook.server.handler.AddressBookPutHandlerBase;

import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class AddressBookPutHandler extends AddressBookPutHandlerBase implements Handler<RoutingContext> {

	public AddressBookPutHandler(Store store) {
		super(store);
	}

	@Override
	public void handle(RoutingContext routingContext) {
		String errorMsg =  "{\"Error\":\"Contact does not exist \"}";
		Contact existingContact = null;
		Contact contact = Json.decodeValue(routingContext.getBodyAsString(), Contact.class);
		if (contact != null) {
			Map<String, Object> context = new HashMap<>();
			context.put(Constants.ACCOUNT_ID, routingContext.request().getParam(Constants.ACCOUNT_ID));
			context.put(Constants.CONTACT_ID, routingContext.request().getParam(Constants.CONTACT_ID));
			context.put(Constants.CONTACT_KEY, contact);
			try {
				existingContact=(Contact)super.handle(context);
			} catch (AddressBookException e) {
				// TODO log error
				errorMsg = "{\"Error\":\""+e.getMessage()+" \"}";
			}
		}
		if (existingContact != null) {
			// send response
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(contact));
		} else {
			routingContext.response().setStatusCode(400)
					.putHeader(Constants.HEADER_CONTENT_TYPE, Constants.APPLICATION_JSON).end(errorMsg);
		}

	}

}
