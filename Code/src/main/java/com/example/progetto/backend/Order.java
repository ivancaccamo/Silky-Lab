package com.example.progetto.backend;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.application.services.DatabaseManager;

public class Order {
	private int ID;
	private int IDuser;
	private Date date;
	private Double tot;
	private ArrayList<SoldProduct> items;
	
	public Order(int iD, int iDuser, Date date, Double tot) throws SQLException {
		DatabaseManager dbManager = new DatabaseManager();
		ID = iD;
		IDuser = iDuser;
		this.date = date;
		this.tot = tot;
		items = dbManager.getSoldProductsByOrderId(iD);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getIDuser() {
		return IDuser;
	}

	public void setIDuser(int iDuser) {
		IDuser = iDuser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getTot() {
		return tot;
	}

	public void setTot(Double tot) {
		this.tot = tot;
	}

	public ArrayList<SoldProduct> getItems() {
		return items;
	}

	public void setItems(ArrayList<SoldProduct> items) {
		this.items = items;
	}

}
