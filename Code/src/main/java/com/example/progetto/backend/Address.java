package com.example.progetto.backend;

public class Address {
	private int ID;
	private String address;
	private String city;
	private int cap;
	private String country;
	private int IDuser;
	
	
	public Address() {
	
	}
	
	public Address(int iD, String address, String city, int cap, String country, int iDuser) {
		
		ID = iD;
		this.address = address;
		this.city = city;
		this.cap = cap;
		this.country = country;
		IDuser = iDuser;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getCap() {
		return cap;
	}
	public void setCap(int cap) {
		this.cap = cap;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getIDuser() {
		return IDuser;
	}
	public void setIDuser(int iDuser) {
		IDuser = iDuser;
	}
	
	}
