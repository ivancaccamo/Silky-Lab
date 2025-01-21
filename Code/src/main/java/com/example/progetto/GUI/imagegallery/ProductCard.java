package com.example.progetto.GUI.imagegallery;

import com.example.progetto.backend.Model;

import java.sql.SQLException;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Current;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;


public class ProductCard extends Div {
	DatabaseManager dbManager = new DatabaseManager();
    public ProductCard(Model model) {
    
        Div productCard = new Div();
        
        
        productCard.addClassName("product-card");
        productCard.addClassNames(Display.FLEX, FlexDirection.COLUMN, AlignItems.CENTER, Padding.MEDIUM,
                BorderRadius.LARGE, Width.FULL);
        productCard.setWidth("340px");
        productCard.getStyle().set("position", "relative") .set("background", "transparent").set("cursor", "pointer")
                .set("transition", "all 0.3s ease-in-out");
        productCard.getStyle().set("box-shadow", "0px 4px 10px rgba(0, 0, 0, 0.1)");
        productCard.getStyle().set("border", "4px solid transparent");

        productCard.getElement().addEventListener("mouseover", e -> productCard.getStyle()
                .set("box-shadow", "0px 4px 15px rgba(0, 0, 0, 0.2)")
                .set("border", "4px solid #ccc"));
        productCard.getElement().addEventListener("mouseout", e -> productCard.getStyle()
                .set("box-shadow", "0px 2px 10px rgba(0, 0, 0, 0.1)")
                .set("border", "4px solid transparent"));

        Image productImage = new Image("Images/" + model.getName() + ".png", "");
        productImage.setWidth("100%");
        productImage.setHeight("400px");
        productImage.getStyle().set("object-fit", "cover");

        Div productInfo = new Div();
        productInfo.getStyle().set("display", "flex");
        productInfo.getStyle().set("flex-direction", "column");
        productInfo.getStyle().set("gap", "10px");
        productInfo.getStyle().set("width", "100%");
        productInfo.addClassName("product-info");

        productInfo.add(new H3(model.getName()));

        Span priceSpan = new Span(model.getPrice() + " €");
        priceSpan.getStyle().set("font-weight", "bold");
        priceSpan.getStyle().set("background", "grey");
        priceSpan.getStyle().set("color", "white");
        priceSpan.getStyle().set("padding", "5px 10px");
        priceSpan.getStyle().set("border-radius", "10px");

        productInfo.add(priceSpan);

        if(Current.getCurrentUser()!=null&&Current.getCurrentUser().getRole().equals("ADMIN")) {
        	Div overlay = new Div();
            overlay.setText("Elimina");
            overlay.getStyle().set("position", "absolute");
            overlay.getStyle().set("top", "0");
            overlay.getStyle().set("left", "0");
            overlay.setWidth("100%");
            overlay.setHeight("100%");
            overlay.getStyle().set("display", "flex");
            overlay.getStyle().set("align-items", "center");
            overlay.getStyle().set("justify-content", "center");
            overlay.getStyle().set("background", "rgba(0, 0, 0, 0.4)");
            overlay.getStyle().set("color", "white");
            overlay.getStyle().set("font-size", "20px");
            overlay.getStyle().set("font-weight", "bold");
            overlay.getStyle().set("opacity", "0");
            overlay.getStyle().set("transition", "opacity 0.3s ease");
            overlay.getStyle().set("border-radius","35px");
    		productCard.getElement().addEventListener("mouseover", e -> overlay.getStyle().set("opacity", "1"));
    		productCard.getElement().addEventListener("mouseout", e -> overlay.getStyle().set("opacity", "0"));
    		productCard.add(productImage, productInfo,overlay);
    		
    		Dialog dialog = new Dialog();
    	    Button cancelButton = new Button("Annulla", event -> dialog.close());

            // Pulsante per confermare
            Button confirmButton = new Button("Conferma", event -> {
            	try {
					dbManager.deleteModelByName(model.getName());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	getUI().ifPresent(ui -> ui.getPage().reload());

                dialog.close(); // Chiudi il dialogo
            });
            cancelButton.getStyle().set("margin-right", "10px");
            confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            confirmButton.getStyle().set("color", "white");

            // Layout dei pulsanti
            HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, confirmButton);
            buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

            // Aggiunta del messaggio e dei pulsanti al dialogo
            Paragraph messageParagraph = new Paragraph("Sei sicuro di voler cancellare questo modello:\n"+model.getName());
            dialog.add(messageParagraph, buttonLayout);
    		productCard.addClickListener(event -> {
    			dialog.open();	
    	    Current.setProductView(model.getName());
    	    
    	    });
    	}else {
    		productCard.add(productImage, productInfo);
    		productCard.addClickListener(event -> {
            Current.setProductView(model.getName());
            getUI().ifPresent(ui -> ui.navigate("product-detail"));
        });
    	}
        

        

        add(productCard);
    }

    public ProductCard() {
        Div productCard = new Div();
        
        productCard.addClassNames(Display.FLEX, FlexDirection.COLUMN, AlignItems.CENTER, Padding.MEDIUM,
                BorderRadius.LARGE, Width.FULL);
        productCard.setWidth("330px");
        productCard.getStyle().set("background", "transparent").set("cursor", "pointer")
                .set("transition", "all 0.3s ease-in-out");
        productCard.getStyle().set("box-shadow", "0px 4px 10px rgba(0, 0, 0, 0.1)");
        productCard.getStyle().set("border", "4px solid transparent");

        productCard.getElement().addEventListener("mouseover", e -> productCard.getStyle()
                .set("box-shadow", "0px 4px 15px rgba(0, 0, 0, 0.2)")
                .set("border", "4px solid #ccc"));
        productCard.getElement().addEventListener("mouseout", e -> productCard.getStyle()
                .set("box-shadow", "0px 2px 10px rgba(0, 0, 0, 0.1)")
                .set("border", "4px solid transparent"));

        Image productImage = new Image("Images/add2.png", "");
        productImage.setWidth("100%");
        productImage.setHeight("400px");
        productImage.getStyle().set("object-fit", "cover");

        Div productInfo = new Div();
        productInfo.getStyle().set("display", "flex");
        productInfo.getStyle().set("flex-direction", "column");
        productInfo.getStyle().set("gap", "10px");
        productInfo.getStyle().set("width", "100%");
        productInfo.addClassName("product-info");

        productInfo.add(new H3("Aggiungi un modello"));

        Span priceSpan = new Span("--.--€");
        priceSpan.getStyle().set("font-weight", "bold");
        priceSpan.getStyle().set("background", "grey");
        priceSpan.getStyle().set("color", "white");
        priceSpan.getStyle().set("padding", "5px 10px");
        priceSpan.getStyle().set("border-radius", "10px");

        productInfo.add(priceSpan);

        

        productCard.add(productImage, productInfo);

        productCard.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("admin")));

        add(productCard);
    }
    
}
