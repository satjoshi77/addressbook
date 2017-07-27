package com.addressbook.server.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.addressbook.common.AddressBookException;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;

public class AddressBookGetHandlerBaseTest {

	@Test
	public void testHandleWhenContactIdDoesNotMatch() throws AddressBookException {
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.getByContactId(null, null)).andReturn(null);
		EasyMock.replay(mock);
		AddressBookGetHandlerBase handler= new AddressBookGetHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		assertNull(handler.handle(context));
		EasyMock.verify(mock);
	}
	@Test
	public void testHandleWhenContactIdMatch() throws AddressBookException {
		Contact contact = new Contact();
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.getByContactId(null, null)).andReturn(contact);
		EasyMock.replay(mock);
		AddressBookGetHandlerBase handler= new AddressBookGetHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		assertEquals(contact,handler.handle(context));
		EasyMock.verify(mock);
		
	}

}
