package com.addressbook.persistence.api;

import java.util.List;

import org.junit.Test;

import com.addressbook.models.Contact;

public class StoreTest {

	@Test
	public void testContract() {
		new Store(){
			@Override
			public Contact put(String accountId, String contactId, Contact contact) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Contact remove(String accountId, String contactId) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Contact> getByStates(String accountId, String states) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Contact> getByAreaCode(String accountId, String areaCode) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Contact getByContactId(String accountId, String contactId) {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

}
