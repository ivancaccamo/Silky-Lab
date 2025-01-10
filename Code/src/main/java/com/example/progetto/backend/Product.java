package com.example.progetto.backend;

import com.vaadin.flow.component.html.Image;

public class Product {
    private int id;
    private String name;
    private String size;
    private String description;
    private double price;
    private String category;
    private int UserID;
    private Image image;
     
    
	public Product(int id, String name, String size, double price, int userID) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.price = price;
		UserID = userID;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	
    
}
