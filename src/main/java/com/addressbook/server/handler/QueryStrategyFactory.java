package com.addressbook.server.handler;

import java.util.Map;

import com.addressbook.common.AddressBookException;
import com.addressbook.common.Constants;
import com.addressbook.persistence.api.Store;

public class QueryStrategyFactory {
	private static QueryStrategy contactIdStrategy = new ContactIdStrategy();
	private static QueryStrategy areaCodeStrategy = new AreaCodeStrategy();
	private static QueryStrategy stateStrategy = new StateStrategy();

	public static QueryStrategy getInstance(Map<String, Object> context) {
		if (context.get(Constants.AREA_CODE) != null) {
			return areaCodeStrategy;
		} else if (context.get(Constants.STATE) != null) {
			return stateStrategy;
		} else {
			return contactIdStrategy;
		}
	}
}
/**
 * interface to support multiple query types
 * @author satish
 *
 */
interface QueryStrategy {
	Object execute(Map<String, Object> context, Store store) throws AddressBookException;
}

/**
 * query based on contact id
 * 
 * @author satish
 *
 */
class ContactIdStrategy implements QueryStrategy {
	@Override
	public Object execute(Map<String, Object> context, Store store) throws AddressBookException {
		String accountId = (String) context.get(Constants.ACCOUNT_ID);
		String contactId = (String) context.get(Constants.CONTACT_ID);
		// pull contact details from store
		return store.getByContactId(accountId, contactId);
	}
}

/**
 * query using state
 * @author satish
 *
 */
class StateStrategy implements QueryStrategy {
	@Override
	public Object execute(Map<String, Object> context, Store store) throws AddressBookException {
		String state = (String) (String) context.get(Constants.STATE);
		String accountId = (String) context.get(Constants.ACCOUNT_ID);
		// pull contact details from store
		return store.getByStates(accountId, state);
	}
}

/**
 * query using area code
 * @author satish
 *
 */
class AreaCodeStrategy implements QueryStrategy {
	@Override
	public Object execute(Map<String, Object> context, Store store) throws AddressBookException {
		String areaCode = (String) (String) context.get(Constants.AREA_CODE);
		String accountId = (String) context.get(Constants.ACCOUNT_ID);
		// pull contact details from store
		return store.getByAreaCode(accountId, areaCode);
	}
}
