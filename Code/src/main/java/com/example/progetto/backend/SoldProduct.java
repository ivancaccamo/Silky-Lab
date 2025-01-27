package com.example.progetto.backend;

public class SoldProduct {
	private int ID;
	private int IDorder;
	private String model;
	private String size;
	
	
	public SoldProduct() {
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getIDorder() {
		return IDorder;
	}
	public void setIDorder(int iDorder) {
		IDorder = iDorder;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
}

