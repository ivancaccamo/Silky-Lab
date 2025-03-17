package com.example.progetto.backend;

import com.example.progetto.GUI.imagegallery.ImageGalleryViewCard;
import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
/**
 * Rappresenta un prodotto specifico, completo di ID, taglia e riferimento a un {@link Model}.
 * <p>
 * Questa classe consente di distinguere le varianti di uno stesso modello in base alla taglia
 * o ad altri attributi che possono essere associati a un singolo articolo nel catalogo.
 * </p>
 */
public class Product{
    private int id;
    private String size;
    private Model model;
	public Product(int id, String size, Model model) {
		this.id = id;
		this.size = size;
		this.model = model;
	}
	public Product() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	
	
     
    
	
}
