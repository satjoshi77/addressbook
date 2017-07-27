package com.addressbook.persistence.api;

import java.util.List;

import com.addressbook.models.Contact;

/**
 * interface to define the contract of a backend store.
 * 
 * @author satish
 *
 */
public interface Store {
	/**
	 * 
	 * @param accountId
	 * @param contactId
	 * @param contact
	 * @return
	 */
	Contact put(String accountId,String contactId,Contact contact);
	
	/**
	 * 
	 * @param accountId
	 * @param contactId
	 * @return
	 */
	Contact remove(String accountId,String contactId);
	
	/**
	 * 
	 * @param accountId
	 * @param states
	 * @return
	 */
	List<Contact> getByStates(String accountId, String states);
	
	/**
	 * 
	 * @param accountId
	 * @param areaCode
	 * @return
	 */
	List<Contact> getByAreaCode(String accountId, String areaCode);
	
	/**
	 * 
	 * @param accountId
	 * @param contactId
	 * @return
	 */
	Contact getByContactId(String accountId, String contactId);
}
