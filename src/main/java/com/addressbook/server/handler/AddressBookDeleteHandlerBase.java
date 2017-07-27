package com.addressbook.server.handler;

import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.persistence.api.Store;

public class AddressBookDeleteHandlerBase implements Handler {
	private Store store;

	public AddressBookDeleteHandlerBase(Store store) {
		this.store = store;
	}

	@Override
	public Object handle(Map<String, Object> context) throws  AddressBookException{
		String accountId = (String) context.get(Constants.ACCOUNT_ID);
		String contactId = (String) context.get(Constants.CONTACT_ID);
		return store.remove(accountId, contactId);
	}

}
