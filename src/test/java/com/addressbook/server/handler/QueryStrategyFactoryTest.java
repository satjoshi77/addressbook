package com.addressbook.server.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import com.addressbook.common.AddressBookException;
import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;

public class QueryStrategyFactoryTest {

	@Test
	public void testContactIdStategy() throws AddressBookException {
		Map<String, Object> context = new HashMap<>();
		Contact contact = new Contact();
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.getByContactId(null, null)).andReturn(contact);
		EasyMock.replay(mock);
		QueryStrategy instance = QueryStrategyFactory.getInstance(context);
		assertTrue(instance instanceof ContactIdStrategy);
		assertEquals(contact, instance.execute(context, mock));
		EasyMock.verify(mock);
	}
	
	@Test
	public void testStateStategy() throws AddressBookException {
		Map<String, Object> context = new HashMap<>();
		context.put("state", "ca");
		Contact contact = new Contact();
		List<Contact> contacts= new ArrayList<>();
		contacts.add(contact);
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.getByStates(null, "ca")).andReturn(contacts);
		EasyMock.replay(mock);
		QueryStrategy instance = QueryStrategyFactory.getInstance(context);
		assertTrue(instance instanceof StateStrategy);
		assertEquals(contacts, instance.execute(context, mock));
		EasyMock.verify(mock);
	}
	@Test
	public void testAreaCodeStategy() throws AddressBookException {
		Map<String, Object> context = new HashMap<>();
		context.put("areaCode", "510");
		Contact contact = new Contact();
		List<Contact> contacts= new ArrayList<>();
		contacts.add(contact);
		Store mock = EasyMock.createMock(Store.class);
		EasyMock.expect(mock.getByAreaCode(null, "510")).andReturn(contacts);
		EasyMock.replay(mock);
		QueryStrategy instance = QueryStrategyFactory.getInstance(context);
		assertTrue(instance instanceof AreaCodeStrategy);
		assertEquals(contacts, instance.execute(context, mock));
		EasyMock.verify(mock);
	}

}
