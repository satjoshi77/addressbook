package com.addressbook.persistence.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.addressbook.persistence.impl.memory.InMemoryStore;

public class StoreFactoryTest {

	@Test
	public void testStoreFactoryCreateForMemoryType() throws Exception {
		assertTrue(StoreFactory.create("memory") instanceof InMemoryStore);
	}
	
	@Test(expected = Exception.class)
	public void testStoreFactoryCreateForDbType() throws Exception {
		StoreFactory.create("db");
	}

}
