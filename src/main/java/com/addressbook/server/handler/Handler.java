package com.addressbook.server.handler;

import java.util.Map;

import com.addressbook.common.AddressBookException;

public interface Handler {

	Object handle(Map<String, Object> context) throws AddressBookException;

}
