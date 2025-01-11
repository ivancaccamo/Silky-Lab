package com.example.progetto.GUI.imagegallery;

<<<<<<< Updated upstream
import com.example.progetto.backend.Current;
=======
import java.util.Arrays;
import java.util.List;

import com.example.progetto.backend.Cart;
import com.example.progetto.backend.CurrentUser;
import com.example.progetto.backend.Product;
>>>>>>> Stashed changes
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Prodotto")
@Route("product-detail")
public class ProductView extends VerticalLayout{
	
	private String product;
    private String description = "Questa è una descrizione generica del prodotto.";
    private String shipping = "Spedizione gratuita per gli ordini superiori a 100 €.";
    private List<String> sizes = Arrays.asList("S", "M", "L", "XL"); // Taglie disponibili
    private static final int MAX_QUANTITY = 10; // Quantità massima acquistabile
    private Product p = new Product(1, "Felpa grigia","", 59.99, "Images/SilkyLabIcon.png");

    public ProductView() {
        // Recupera il prodotto selezionata da CurrentUser
        product = Current.getProductView();

        if (product == null || product.isEmpty()) {
            product = "Prodotto non trovato";
        }

        // Layout
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        // Titolo
        H2 header = new H2(product);
        header.getStyle().set("margin-bottom", "10px");
        layout.add(header);
        
     // Bottone per tornare indietro
        Button backButton = new Button("Torna indietro", new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("category-detail"));
        });
        
        // Descrizione del prodotto
        H4 sezione1 = new H4("Dettagli del prodotto");
        Paragraph productDescription = new Paragraph(description);
        H4 sezione2= new H4("Shipping");
        Paragraph shippingDetail = new Paragraph(shipping);

        // Selezione delle taglie
        ComboBox<String> sizeSelector = new ComboBox<>();
        sizeSelector.setItems(sizes);
        sizeSelector.setPlaceholder("Seleziona la taglia");
        
        // Selezione quantità
        IntegerField quantityField = new IntegerField();
        quantityField.setValue(1);
        quantityField.setMin(1);
        quantityField.setMax(MAX_QUANTITY);
        quantityField.setStep(1);
        quantityField.setWidth("60px");
        
        // Bottone "-" per diminuire la quantità
        Button decreaseButton = new Button(new Icon(VaadinIcon.MINUS), event -> {
            int currentValue = quantityField.getValue();
            if (currentValue > 1) {
                quantityField.setValue(currentValue - 1);
            }
        });

        // Bottone "+" per aumentare la quantità
        Button increaseButton = new Button(new Icon(VaadinIcon.PLUS), event -> {
            int currentValue = quantityField.getValue();
            if (currentValue < MAX_QUANTITY) {
                quantityField.setValue(currentValue + 1);
            }
        });

        // Layout orizzontale per quantità con bottoni
        HorizontalLayout quantityLayout = new HorizontalLayout(decreaseButton, quantityField, increaseButton);
        quantityLayout.setAlignItems(Alignment.CENTER);
        
        // Bottone "Aggiungi al carrello"
        Button addToCartButton = new Button("Aggiungi al carrello", new Icon(VaadinIcon.CART));
        addToCartButton.addClickListener(event -> {
            String selectedSize = sizeSelector.getValue();
            int selectedQuantity = quantityField.getValue();

            if (selectedSize == null) {
                Notification.show("Seleziona una taglia prima di aggiungere al carrello", 3000, Notification.Position.MIDDLE);
            } else if (selectedQuantity < 1) {
                Notification.show("Seleziona una quantità valida", 3000, Notification.Position.MIDDLE);
            } else if (selectedQuantity > MAX_QUANTITY) {
                Notification.show("Puoi acquistare massimo " + MAX_QUANTITY + " unità di questo prodotto", 3000, Notification.Position.MIDDLE);
            } else {
                Cart.addItem(p, selectedQuantity);
                Notification.show("Prodotto aggiunto al carrello!", 3000, Notification.Position.MIDDLE);
            }
        });
        addToCartButton.getStyle().set("margin-bottom", "20px");
        
        layout.add(header, backButton);
        add(layout, sizeSelector, quantityLayout, addToCartButton, sezione1, productDescription, sezione2, shippingDetail);
    }
}
