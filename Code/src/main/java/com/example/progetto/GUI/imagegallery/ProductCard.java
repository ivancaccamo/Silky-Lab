package com.example.progetto.GUI.imagegallery;

import com.example.progetto.backend.Model;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Product;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

public class ProductCard extends Div {

    public ProductCard(Model model) {
        //Card del prodotto
        Div productCard = new Div();
        productCard.addClassName("product-card");
        productCard.addClassNames(Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE, Width.FULL);
        productCard.setWidth("100%"); // Larghezza massima della card
        productCard.getStyle().set("background", "transparent").set("cursor", "pointer")
        .set("transition", "all 0.3s ease-in-out");
        productCard.setWidth("330px"); // Imposta una larghezza per ogni card
        productCard.getStyle().set("box-shadow", "0px 4px 10px rgba(0, 0, 0, 0.1)");
        productCard.getStyle().set("border", "4px solid transparent");
        productCard.getElement().addEventListener("mouseover", e -> productCard.getStyle()
        		.set("box-shadow", "0px 4px 15px rgba(0, 0, 0, 0.2)")
        		.set("border", "4px solid #ccc"));
        productCard.getElement().addEventListener("mouseout", e -> productCard.getStyle()
        		.set("box-shadow", "0px 2px 10px rgba(0, 0, 0, 0.1)")
        		.set("border", "4px solid transparent"));

        // Immagine del prodotto
        Image productImage = new Image(model.getImageUrl(), "");
        productImage.setWidth("100%");
        productImage.getStyle().set("object-fit", "cover");

        // Nome e prezzo
        Div productInfo = new Div();
        
        productInfo.getStyle().set("display", "flex");
        productInfo.getStyle().set("flex-direction", "column");
        productInfo.getStyle().set("gap", "10px");
        productInfo.getStyle().set("width", "100%");
        
        productInfo.addClassName("product-info");
        productInfo.add(new H3(model.getName()));

        Span priceSpan = new Span(model.getPrice() + " €");
        priceSpan.getStyle().set("font-weight", "bold");
        priceSpan.getStyle().set("background", "grey"); // Colore di sfondo verde
        priceSpan.getStyle().set("color", "white"); // Colore del testo (bianco)
        priceSpan.getStyle().set("padding", "5px 10px"); // Padding per rendere il prezzo più visibile
        priceSpan.getStyle().set("border-radius", "10px");  // Bordi arrotondati

        productInfo.add(priceSpan);

        // Aggiungi immagine e info alla card
        productCard.add(productImage, productInfo);

        // Gestisci il click per navigare alla pagina dei dettagli
        productCard.addClickListener(event -> {
            Current.setProductView(model.getName());
            getUI().ifPresent(ui -> {
                ui.navigate("product-detail");
            });
        });

        // Aggiungi la card al contenitore
        add(productCard);
    }
}