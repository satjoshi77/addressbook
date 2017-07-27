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

public class AddressBookPostHandlerBaseTest {

	@Test(expected=AddressBookException.class)
	public void testWhenContactToPostIsNull() throws Exception {
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.put(null, null,null)).andReturn(null);
		EasyMock.replay(mock);
		AddressBookPostHandlerBase handler= new AddressBookPostHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		assertNull(handler.handle(context));
		EasyMock.verify(mock);
	}
	
	@Test
	public void testWhenContactDoesNotExist() throws Exception {
		Contact contactIn= new Contact();
		Contact contactOut= new Contact();
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.put(null, null,contactIn)).andReturn(contactOut);
		EasyMock.expect(mock.getByContactId(null, null)).andReturn(null);
		EasyMock.replay(mock);
		AddressBookPostHandlerBase handler= new AddressBookPostHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		context.put("contactKey", contactIn);
		assertEquals(contactIn, handler.handle(context));;
		EasyMock.verify(mock);
	}
	
	@Test(expected=AddressBookException.class)
	public void testWhenContactAlreadyExists() throws Exception {
		Contact contactIn= new Contact();
		Contact contactOut= new Contact();
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.put(null, null,contactIn)).andReturn(contactOut);
		EasyMock.expect(mock.getByContactId(null, null)).andReturn(contactOut);
		EasyMock.replay(mock);
		AddressBookPostHandlerBase handler= new AddressBookPostHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		context.put("contactKey", contactIn);
		handler.handle(context);
		EasyMock.verify(mock);
	}

}
