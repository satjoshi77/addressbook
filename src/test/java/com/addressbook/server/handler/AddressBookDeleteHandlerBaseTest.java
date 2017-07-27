
package com.addressbook.server.handler;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.addressbook.common.AddressBookException;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;

public class AddressBookDeleteHandlerBaseTest {
	@Before
	public void setup() {
		
	}

	@Test
	public void testHandleWhenRemoveWasFailed() throws AddressBookException {
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.remove(null, null)).andReturn(null);
		EasyMock.replay(mock);
		AddressBookDeleteHandlerBase handler= new AddressBookDeleteHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		handler.handle(context);
		EasyMock.verify(mock);
	}
	@Test
	public void testHandleWhenRemoveWasSuccessFul() throws AddressBookException {
		Contact contact = new Contact();
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.remove(null, null)).andReturn(contact);
		EasyMock.replay(mock);
		AddressBookDeleteHandlerBase handler= new AddressBookDeleteHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		assertEquals(contact,handler.handle(context));
		EasyMock.verify(mock);
	}

}
