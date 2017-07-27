package com.addressbook.server.handler;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.persistence.api.Store;

public class AddressBookPutHandlerBaseTest {

	@Test(expected=AddressBookException.class)
	public void testWhenContactToPostIsNull() throws Exception {
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.put(null, null,null)).andReturn(null);
		EasyMock.replay(mock);
		AddressBookPutHandlerBase handler= new AddressBookPutHandlerBase(mock);
		Map<String, Object> context= new HashMap<>();
		handler.handle(context);
		assertNull(context.get(Constants.CONTACT_KEY));
		EasyMock.verify(mock);
	}

}
