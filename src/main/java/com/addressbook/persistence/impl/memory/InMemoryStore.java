package com.addressbook.persistence.impl.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.addressbook.models.Contact;
import com.addressbook.persistence.api.Store;
/**
 * This class implements Store interface. it represent in-memory representation  of backend store.
 * @author satish
 *
 */
public class InMemoryStore implements Store {
	//accountId to (contactId to contact) mapping
	private Map<String, Map<String, Contact>> contactIdToContactsMap = new HashMap<>();
	//accountId to (state to contact map (contactId to contact)) mapping
	private Map<String, Map<String, Map<String, Contact>>> stateToContactsMap = new HashMap<>();
	//accountId to (area code to contact map (contactId to contact)) mapping
	private Map<String, Map<String, Map<String, Contact>>> areaCodeToContactsMap = new HashMap<>();

	public InMemoryStore() {
	}

	@Override
	public synchronized Contact put(String accountId, String contactId, Contact contact) {
		if (contact == null) {
			return null;//TODO throw exception
		}
		//to support query by states need to  contacts by states
		putByState(accountId, contact);
		//to support query by area code need to organize contacts by area code
		putByAreaCode(accountId, contact);
		//to support search by account id need to oraganize by account id
		return putByContactId(accountId, contactId, contact);
	}

	/**
	 * organize by accountId
	 * 
	 * @param accountId
	 * @param contactId
	 * @param contact
	 * @return
	 */
	Contact putByContactId(String accountId, String contactId, Contact contact) {
		Map<String, Contact> contacts = contactIdToContactsMap.get(accountId);
		// ideally there should be another rest endpoint to create an
		// account, if account doesn't exist then put should fail
		// for now we will go ahead and create account if it doesn't exist
		if (contacts == null) {
			contacts = new HashMap<>();
			contactIdToContactsMap.put(accountId, contacts);
		}
		return contacts.put(contactId, contact);
	}

	/**
	 * organize by state
	 * @param accountId
	 * @param contact
	 */
	void putByState(String accountId, Contact contact) {
		//get state to contacts map for a given accountId
		Map<String, Map<String, Contact>> allContacts = stateToContactsMap.get(accountId);
		//if no contacts then create one
		if (allContacts == null) {
			allContacts = new HashMap<>();
			stateToContactsMap.put(accountId, allContacts);
		}
		if (contact != null && contact.getAddress() != null && contact.getAddress().getState() != null) {
			String state = contact.getAddress().getState().trim().toLowerCase();
			//get contacts for a state
			Map<String, Contact> stateContacts = allContacts.get(state);
			//if no map then create empty map
			if (stateContacts == null) {
				stateContacts = new HashMap<>();
				allContacts.put(state, stateContacts);
			}
			//put in the map
			stateContacts.put(contact.getId(), contact);
		}
	}

	/**
	 * organize by area code
	 * 
	 * @param accountId
	 * @param contact
	 */
	void putByAreaCode(String accountId, Contact contact) {
		//get area code to contacts mapping for a given account id
		Map<String, Map<String, Contact>> allContacts = areaCodeToContactsMap.get(accountId);
		//if null create empty map
		if (allContacts == null) {
			allContacts = new HashMap<>();
			areaCodeToContactsMap.put(accountId, allContacts);
		}
		if (contact != null && contact.getPhoneNumber() != null && contact.getPhoneNumber().length() >= 3) {
			// assuming the format is xxx-xxx-xxxx
			String areaCode = contact.getPhoneNumber().substring(0, 3);
			//get contact for a given area code
			Map<String, Contact> localContacts = allContacts.get(areaCode);
			//if null create an empty map
			if (localContacts == null) {
				localContacts = new HashMap<>();
				allContacts.put(areaCode, localContacts);
			}
			//put in the map
			localContacts.put(contact.getId(), contact);
		}
	}
	
	/**
	 * get a contact for a given account Id and contact id combination
	 */
	@Override
	public synchronized Contact getByContactId(String accountId, String contactId) {
		//get all contacts for an account
		Map<String, Contact> allContacts = contactIdToContactsMap.get(accountId);
		if (allContacts != null) {
			//return a  contact for a contactId
			return allContacts.get(contactId);
		}
		return null;
	}
	/**
	 * Query by states. for example state=CA,VA
	 * get all the contacts within the list of states for a given accountId
	 */
	@Override
	public synchronized List<Contact> getByStates(String accountId, String states) {
		if (states != null) {
			Map<String, Map<String, Contact>> allAccounts = stateToContactsMap.get(accountId);
			if (allAccounts != null && states != null) {
				String[] statesArray = states.split(",");
				List<Contact> filteredContacts = new ArrayList<>();
				for (String state : statesArray) {
					Map<String, Contact> stateContacts = allAccounts.get(state.trim().toLowerCase());
					if (stateContacts != null) {
						filteredContacts.addAll(stateContacts.values());
					}
				}
				return filteredContacts;
			}
		}
		return null;
	}
	
	/**
	 * For a given area code and account id combination get all the contacts
	 */

	@Override
	public synchronized List<Contact> getByAreaCode(String accountId, String areaCode) {
		Map<String, Map<String, Contact>> allAccounts = areaCodeToContactsMap.get(accountId);
		if (allAccounts != null) {
			List<Contact> filteredContacts = new ArrayList<>();
			Map<String, Contact> localContacts = allAccounts.get(areaCode);
			if (localContacts != null) {
				filteredContacts.addAll(localContacts.values());
			}
			return filteredContacts;
		}
		return null;
	}

	/**
	 * remove contact for a given contact id and account id combination
	 */
	@Override
	public synchronized Contact remove(String accountId, String contactId) {
		// remove from contactId mapping
		Map<String, Contact> contacts = contactIdToContactsMap.get(accountId);
		Contact contact = null;
		if (contacts != null) {
			contact = contacts.remove(contactId);
		}
		if (contact != null) {
			// remove from state mapping
			if (contact.getAddress() != null && contact.getAddress().getState() != null) {
				Map<String, Map<String, Contact>> allContacts = stateToContactsMap.get(accountId);
				if (allContacts != null) {
					Map<String, Contact> stateContacts = allContacts.get(contact.getAddress().getState());
					if (stateContacts != null) {
						stateContacts.remove(contact.getId());
					}
				}
			}
			// remove from area code mapping
			if (contact.getPhoneNumber() != null && contact.getPhoneNumber().length() >= 3) {
				Map<String, Map<String, Contact>> allContacts = areaCodeToContactsMap.get(accountId);
				if (allContacts != null) {
					Map<String, Contact> localContacts = allContacts
							.get(contact.getPhoneNumber().substring(0, 3));
					if (localContacts != null) {
						localContacts.remove(contact.getId());
					}
				}
			}
		}
		return contact;
	}
	
	Map<String, Map<String, Contact>> getContactIdToContactsMap(){
		return contactIdToContactsMap;
	}
	Map<String, Map<String, Map<String, Contact>>> getStateToContactsMap(){
		return stateToContactsMap;
	}
	Map<String, Map<String, Map<String, Contact>>> getAreaCodeToContactsMap(){
		return areaCodeToContactsMap;
	}
}
