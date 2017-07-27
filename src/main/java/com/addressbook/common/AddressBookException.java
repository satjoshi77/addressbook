package com.addressbook.common;

@SuppressWarnings("serial")
public class AddressBookException extends Exception{

	public AddressBookException(String message, Exception e) {
		super(message,e);
	}

	public AddressBookException(String message) {
		super(message);
	}

}
