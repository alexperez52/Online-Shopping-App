package shopping.model;

import java.io.Serializable;

public class Address implements Serializable{ //holds user's address for shipping purposes and tax
	private String houseNumber;
	private String streetAddress;
	private String city;
	private String state;
	private String country;

	public Address(String houseNumber, String streetAddress, String city, String state, String country) {
		this.houseNumber = houseNumber;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getFullAddress() {
		return houseNumber + " " + streetAddress + " " + city + " " + state + " " + country;
	}

	@Override
	public String toString() {
		return "Address [houseNumber=" + houseNumber + ", streetAddress=" + streetAddress + ", city=" + city
				+ ", state=" + state + ", country=" + country + "]";
	}
	
	
}
