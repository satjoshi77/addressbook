package com.addressbook.persistence.impl;

import com.addressbook.persistence.api.Store;
import com.addressbook.persistence.impl.memory.InMemoryStore;

/**
 * Factory for backen store implementation
 * 
 * @author satish
 *
 */
public class StoreFactory {
	public static Store create(String type) throws Exception {
		if ("memory".equals(type)) {
			return new InMemoryStore();
		} else {
			throw new Exception("Unsupported type:" + type);
		}
	}

}
