package com.addressbook.persistence.impl.memory;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.addressbook.models.Address;
import com.addressbook.models.Contact;

public class InMemoryStoreTest {

	private Contact contact1;
	private Contact contact2;
	@Before
	public void setup() {
		contact1= new Contact();
		contact1.setAddress(new Address("fre","us","ca","123 street","94444"));
		contact1.setEmail("abc@xyz.com");
		contact1.setFirstName("sat");
		contact1.setId("222");
		contact1.setLastContactedDate("07-27-2017");
		contact1.setLastName("jos");
		contact1.setPhoneNumber("510-123-1234");
		
		contact2= new Contact();
		contact2.setAddress(new Address("rest","us","va","789 ave","24444"));
		contact2.setEmail("aaa@xyz.com");
		contact2.setFirstName("pat");
		contact2.setId("999");
		contact2.setLastContactedDate("07-27-2016");
		contact2.setLastName("man");
		contact2.setPhoneNumber("703-123-1234");
	}

	@Test
	public void testPutWhenContactIsNotNull() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		store.put("888", "999", contact2);
		assertTrue(store.getContactIdToContactsMap().get("111").get("222").equals(contact1));
		assertTrue(store.getStateToContactsMap().get("111").get("ca").get("222").equals(contact1));
		assertTrue(store.getAreaCodeToContactsMap().get("111").get("510").get("222").equals(contact1));
		
		assertTrue(store.getContactIdToContactsMap().get("888").get("999").equals(contact2));
		assertTrue(store.getStateToContactsMap().get("888").get("va").get("999").equals(contact2));
		assertTrue(store.getAreaCodeToContactsMap().get("888").get("703").get("999").equals(contact2));
	}
	@Test
	public void testPutWhenContactIsNull() {
		InMemoryStore store= new InMemoryStore();
		assertNull(store.put("111", "222", null));
	}
	
	@Test
	public void testPutByContactIdWhenContactIsNotNull() {
		InMemoryStore store= new InMemoryStore();
		store.putByContactId("111", "222", contact1);
		assertTrue(store.getContactIdToContactsMap().get("111").get("222").equals(contact1));
	}
	@Test
	public void testPutByStateWhenContactIsNotNull() {
		InMemoryStore store= new InMemoryStore();
		store.putByState("111", contact1);
		assertTrue(store.getStateToContactsMap().get("111").get("ca").get("222").equals(contact1));
	}
	@Test
	public void testPutByAreaCodeWhenContactIsNotNull() {
		InMemoryStore store= new InMemoryStore();
		store.putByAreaCode("111", contact1);
		assertTrue(store.getAreaCodeToContactsMap().get("111").get("510").get("222").equals(contact1));
	}
	@Test
	public void testGetByContactId() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		assertTrue(store.getByContactId("111", "222").equals(contact1));
	}
	
	@Test
	public void testGetByStates() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		assertTrue(store.getByStates("111", "CA").get(0).equals(contact1));
	}
	@Test
	public void testGetByStatesWhenMoreThanOneStateExist() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		store.put("111", "999", contact2);
		assertTrue(store.getByStates("111", "CA,VA").size()==2);
		assertTrue(store.getByStates("111", "CA,VA").get(0).equals(contact1));
		assertTrue(store.getByStates("111", "CA,VA").get(1).equals(contact2));
	}
	@Test
	public void testGetByStatesWhenOneStatePerAccount() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		store.put("888", "999", contact2);
		assertTrue(store.getByStates("111", "CA,VA").size()==1);
		assertTrue(store.getByStates("111", "CA,VA").get(0).equals(contact1));
	}
	@Test
	public void testGetByStatesWhenStateDoesNotExist() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		assertTrue(store.getByStates("111", "FL").size()==0);
	}
	
	@Test
	public void testGetByAreaCode() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		assertTrue(store.getByAreaCode("111", "510").get(0).equals(contact1));
	}
	@Test
	public void testGetByAreaCodeWhenAreaCodeDoesNotExist() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		assertTrue(store.getByAreaCode("111", "650").size()==0);
	}
	
	@Test
	public void testRemoveWhenBothAccountIdAndContactIdExist() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		store.put("888", "999", contact2);
		Contact returnedContact=store.remove("111", "222");
		assertTrue(contact1.equals(returnedContact));
		assertNull(store.getContactIdToContactsMap().get("111").get("222"));
		assertNull(store.getStateToContactsMap().get("111").get("ca").get("222"));
		assertNull(store.getAreaCodeToContactsMap().get("111").get("510").get("222"));
		
		assertTrue(store.getContactIdToContactsMap().get("888").get("999").equals(contact2));
		assertTrue(store.getStateToContactsMap().get("888").get("va").get("999").equals(contact2));
		assertTrue(store.getAreaCodeToContactsMap().get("888").get("703").get("999").equals(contact2));
	}
	@Test
	public void testRemoveWhenAccountIdDoesNotExist() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		store.put("888", "999", contact2);
		Contact returnedContact=store.remove("222", "222");
		assertNull(returnedContact);
		assertTrue(store.getContactIdToContactsMap().get("111").get("222").equals(contact1));
		assertTrue(store.getStateToContactsMap().get("111").get("ca").get("222").equals(contact1));
		assertTrue(store.getAreaCodeToContactsMap().get("111").get("510").get("222").equals(contact1));
		
		assertTrue(store.getContactIdToContactsMap().get("888").get("999").equals(contact2));
		assertTrue(store.getStateToContactsMap().get("888").get("va").get("999").equals(contact2));
		assertTrue(store.getAreaCodeToContactsMap().get("888").get("703").get("999").equals(contact2));

	}
	@Test
	public void testRemoveWhenAccountIdExistsButContactIdDoesNot() {
		InMemoryStore store= new InMemoryStore();
		store.put("111", "222", contact1);
		Contact returnedContact=store.remove("111", "111");
		assertNull(returnedContact);
		assertTrue(store.getContactIdToContactsMap().get("111").get("222").equals(contact1));
		assertTrue(store.getStateToContactsMap().get("111").get("ca").get("222").equals(contact1));
		assertTrue(store.getAreaCodeToContactsMap().get("111").get("510").get("222").equals(contact1));
		
	}

}
