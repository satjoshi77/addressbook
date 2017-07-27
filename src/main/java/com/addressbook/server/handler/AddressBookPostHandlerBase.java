package com.addressbook.server.handler;

import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.common.Utils;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;

public class AddressBookPostHandlerBase implements Handler {

	private Store store;

	public AddressBookPostHandlerBase(Store store) {
		this.store = store;
	}

	@Override
	public Object handle(Map<String, Object> context) throws AddressBookException {
		Contact contact = (Contact) context.get(Constants.CONTACT_KEY);
		if (contact != null) {
			contact.setLastContactedDate(Utils.getDate());
			Contact existingContact = null;
			synchronized (store) {
				existingContact = store.getByContactId((String) context.get(Constants.ACCOUNT_ID), contact.getId());
				if (existingContact == null) {
					store.put((String) context.get(Constants.ACCOUNT_ID), contact.getId(), contact);
				}
			}
			if (existingContact != null) {
				throw new AddressBookException("Contact already exists");
			}
		} else {
			throw new AddressBookException("Contact is null");
		}
		return contact;

	}

}
