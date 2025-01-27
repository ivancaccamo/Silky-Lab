package com.example.progetto.GUI.imagegallery;

import com.example.progetto.backend.Model;

import java.sql.SQLException;
import java.text.DecimalFormat;

import com.example.application.services.DatabaseManager;
import com.example.progetto.GUI.dialogs.QuantityManagerDialog;
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
        DecimalFormat df = new DecimalFormat("0.00");
        String price = df.format(model.getPrice());
        Span priceSpan = new Span(price + " €");
        priceSpan.getStyle().set("font-weight", "bold");
        priceSpan.getStyle().set("background", "grey");
        priceSpan.getStyle().set("color", "white");
        priceSpan.getStyle().set("padding", "5px 10px");
        priceSpan.getStyle().set("border-radius", "10px");

        productInfo.add(priceSpan);

        if(Current.getCurrentUser()!=null&&Current.getCurrentUser().getRole().equals("ADMIN")) {
        	Div overlay2 = new Div();
            Span text2 = new Span("Modifica quantità");
            overlay2.add(text2);
            text2.getStyle().set("font-size","30px");
            overlay2.getStyle().set("position", "absolute");
            overlay2.getStyle().set("top", "0");
            overlay2.getStyle().set("left", "0");
            overlay2.setWidth("100%");
            overlay2.setHeight("49.8%");
            overlay2.getStyle().set("display", "flex");
            overlay2.getStyle().set("align-items", "center");
            overlay2.getStyle().set("justify-content", "center");
            overlay2.getStyle().set("background", "rgba(0, 0, 0, 0.4)");
            overlay2.getStyle().set("color", "white");
            overlay2.getStyle().set("font-size", "20px");
            overlay2.getStyle().set("font-weight", "bold");
            overlay2.getStyle().set("opacity", "0");
            overlay2.getStyle().set("transition", "opacity 0.3s ease");
            overlay2.getStyle().set("border-top-left-radius","35px");
            overlay2.getStyle().set("border-top-right-radius","35px");
    		
    		overlay2.getElement().addEventListener("mouseover", e -> {
    			text2.getStyle().set("transform", "scale(1.3)");
    			text2.getElement().getStyle().set("transition", "transform 0.3s ease");
    		});
    		overlay2.getElement().addEventListener("mouseout", e -> {
    			text2.getElement().getStyle().set("transform", "scale(1)");
    			text2.getElement().getStyle().set("transition", "transform 0.3s ease");
    		});
        	Div overlay1 = new Div();
            Span text1 = new Span("Elimina");
            text1.getStyle().set("font-size","30px");
            overlay1.add(text1);
            overlay1.getStyle().set("position", "absolute");
            overlay1.getStyle().set("bottom", "0");
            overlay1.getStyle().set("left", "0");
            overlay1.setWidth("100%");
            overlay1.setHeight("49.8%");
            overlay1.getStyle().set("display", "flex");
            overlay1.getStyle().set("align-items", "center");
            overlay1.getStyle().set("justify-content", "center");
            overlay1.getStyle().set("background", "rgba(0, 0, 0, 0.4)");
            overlay1.getStyle().set("color", "white");
            overlay1.getStyle().set("font-size", "20px");
            overlay1.getStyle().set("font-weight", "bold");
            overlay1.getStyle().set("opacity", "0");
            overlay1.getStyle().set("transition", "opacity 0.3s ease");
            overlay1.getStyle().set("border-bottom-left-radius","35px");
            overlay1.getStyle().set("border-bottom-right-radius","35px");
    		
    		overlay1.getElement().addEventListener("mouseover", e -> {
    			text1.getStyle().set("transform", "scale(1.3)");
    			text1.getElement().getStyle().set("transition", "transform 0.3s ease");
    		});
    		overlay1.getElement().addEventListener("mouseout", e -> {
    			text1.getElement().getStyle().set("transform", "scale(1)");
    			text1.getElement().getStyle().set("transition", "transform 0.3s ease");
    		});
    		productCard.getElement().addEventListener("mouseover", e -> overlay1.getStyle().set("opacity", "1"));
    		productCard.getElement().addEventListener("mouseout", e -> overlay1.getStyle().set("opacity", "0"));
    		productCard.getElement().addEventListener("mouseover", e -> overlay2.getStyle().set("opacity", "1"));
    		productCard.getElement().addEventListener("mouseout", e -> overlay2.getStyle().set("opacity", "0"));
    		productCard.add(productImage, productInfo,overlay2,overlay1);
    		
    		Dialog dialog = new Dialog();
    	    Button cancelButton = new Button("Annulla", event -> dialog.close());

            // Pulsante per confermare
            Button confirmButton = new Button("Conferma", event -> {
            	try {
					dbManager.deleteModelByName(model.getName());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            	getUI().ifPresent(ui -> ui.getPage().reload());

                dialog.close(); // Chiudi il dialogo
            });
            confirmButton.getStyle().set("cursor", "pointer");
            confirmButton.getStyle().set("cursor", "pointer");
            cancelButton.getStyle().set("margin-right", "10px");
            confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            confirmButton.getStyle().set("color", "white");

            // Layout dei pulsanti
            HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, confirmButton);
            buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

            // Aggiunta del messaggio e dei pulsanti al dialogo
            Paragraph messageParagraph = new Paragraph("Sei sicuro di voler cancellare questo modello:\n"+model.getName());
            dialog.add(messageParagraph, buttonLayout);
    		overlay1.addClickListener(event -> {
    			dialog.open();	
    	        	    });
    		overlay2.addClickListener(event -> {
    			QuantityManagerDialog qntDialog;
				try {
					qntDialog = new QuantityManagerDialog(model.getName(),model);
					qntDialog.open();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    				   	    
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
