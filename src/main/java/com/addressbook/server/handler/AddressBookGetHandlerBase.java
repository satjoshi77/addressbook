package com.addressbook.server.handler;

import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.persistence.api.Store;

public class AddressBookGetHandlerBase implements Handler {
	private Store store;

	public AddressBookGetHandlerBase(Store store) {
		this.store = store;
	}

	@Override
	public Object handle(Map<String, Object> context) throws AddressBookException {
		//use the right strategy implementation
		return QueryStrategyFactory.getInstance(context).execute(context, store);
	}

}


