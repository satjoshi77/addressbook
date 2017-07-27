package com.addressbook.models;

import java.util.Objects;

/**
 * Class to represent a User for address book
 * 
 * @author satish
 *
 */
public class Contact {
	private Address address;
	private String email;
	private String firstName;
	private String id;
	private String lastContactedDate;
	private String lastName;
	private String phoneNumber;
	
	public Contact(){
		
	}

	public Contact(Address address, String email, String firstName, String lastContactedDate, String lastName,
			String phoneNumber) {
		this.address = address;
		this.email = email;
		this.firstName = firstName;
		this.lastContactedDate = lastContactedDate;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastContactedDate() {
		return lastContactedDate;
	}

	public void setLastContactedDate(String lastContactedDate) {
		this.lastContactedDate = lastContactedDate;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Contact) {
			Contact o = (Contact) obj;
			if (Objects.equals(id, o.id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Got to override hashCode() too as we override equals
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
