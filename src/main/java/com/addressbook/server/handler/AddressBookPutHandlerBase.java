package com.addressbook.server.handler;

import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.common.Utils;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;

public class AddressBookPutHandlerBase implements Handler {
	private Store store;

	public AddressBookPutHandlerBase(Store store) {
		this.store = store;
	}

	@Override
	public Object handle(Map<String, Object> context) throws AddressBookException {
		Contact contact = (Contact) context.remove(Constants.CONTACT_KEY);
		if (contact != null) {
			contact.setLastContactedDate(Utils.getDate());
			String accountId = (String) context.get(Constants.ACCOUNT_ID);
			String contactId = (String) context.get(Constants.CONTACT_ID);
			return store.put(accountId, contactId, contact);
		}
		else{
			throw new AddressBookException("Contact is null.");
		}
	}

}
