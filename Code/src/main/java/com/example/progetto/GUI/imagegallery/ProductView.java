package com.example.progetto.GUI.imagegallery;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Cart;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Model;
import com.example.progetto.backend.Product;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

@PageTitle("Prodotto")
@Route("product-detail")
public class ProductView extends VerticalLayout {

    private String productName = Current.getProductView(); // Nome del prodotto attuale
    private List<String> sizes = Arrays.asList("S", "M", "L", "XL"); // Taglie disponibili
    private String selectedSize; // Taglia selezionata
    private static final int MAX_QUANTITY = 5; // Quantità massima acquistabile
    private Product p = new Product(); // Prodotto attuale
    private Model model = new Model(); // Modello del prodotto
    DatabaseManager dbManager = new DatabaseManager(); // Gestore del database
    private String detail = "Designed and made in Italy."; // Dettagli del prodotto
    private String shipping = "Spedizione gratuita per gli ordini superiori a 100 €."; // Info sulla spedizione
    private Button previouslySelectedButton = null; // Memorizza l'ultimo pulsante taglia selezionato
    private int max; // Quantità massima disponibile per la taglia selezionata

    public ProductView() {
        try {
            // Recupera il modello del prodotto dal database
            model = dbManager.returnModelByName(productName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Disponibilità delle taglie
        Map<String, Integer> sizeAvailability = new HashMap<>();
        for (String size : sizes) {
            try {
                // Recupera la quantità disponibile per ogni taglia
                int availableQuantity = dbManager.countRecords(model, size);
                if (availableQuantity > MAX_QUANTITY) availableQuantity = MAX_QUANTITY;
                sizeAvailability.put(size, availableQuantity);
            } catch (SQLException e) {
                e.printStackTrace();
                sizeAvailability.put(size, 0); // Assume disponibilità zero in caso di errore
            }
        }

        // Configurazione immagine prodotto
        Image image = new Image("/Images/" + model.getName() + ".png", "");
        image.setClassName("product-image");
       
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn = new VerticalLayout();
        H2 h2 = new H2(model.getName()); // Nome del prodotto
        Paragraph textMedium = new Paragraph(model.getDescription()); // Descrizione del prodotto
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");

        H3 h3 = new H3(model.getPrice() + " euro"); // Prezzo del prodotto

        H4 sezione1 = new H4("Dettagli del prodotto");
        H4 sezione2 = new H4("Spedizione");
        
        // Campo per selezionare la quantità
        IntegerField quantityField = new IntegerField();
        quantityField.setValue(1); // Valore iniziale
        quantityField.setMin(1); // Quantità minima
        quantityField.setMax(MAX_QUANTITY); // Quantità massima
        quantityField.setStep(1); // Incremento
        quantityField.setWidth("80px");
        
        max = MAX_QUANTITY; // Imposta la quantità massima iniziale
        
        // Pulsanti per aumentare/diminuire la quantità
        Button decreaseButton = new Button(new Icon(VaadinIcon.MINUS), event -> {
            int currentValue = quantityField.getValue();
            if (currentValue > 1) {
                quantityField.setValue(currentValue - 1);
            }
        });

        Button increaseButton = new Button(new Icon(VaadinIcon.PLUS), event -> {
            int currentValue = quantityField.getValue();
            if (currentValue < max) {
                quantityField.setValue(currentValue + 1);
            }
        });

        HorizontalLayout quantityLayout = new HorizontalLayout(decreaseButton, quantityField, increaseButton);
        quantityLayout.setAlignItems(Alignment.CENTER);
        
        // Layout per i pulsanti delle taglie
        HorizontalLayout sizeLayout = new HorizontalLayout();
        sizeLayout.setSpacing(true);
        sizeLayout.setAlignItems(Alignment.CENTER);
        
        // Aggiorna i pulsanti delle taglie in base alla disponibilità
        refreshSizeButtons(sizeAvailability, sizeLayout, quantityField);       

        H5 selectSizeLabel = new H5("Seleziona la taglia"); // Etichetta per la selezione taglie
        H5 selectQuantityLabel = new H5("Seleziona la quantità"); // Etichetta per la selezione quantità

        // Bottone per aggiungere il prodotto al carrello
        Button addToCartButton = new Button("Aggiungi al carrello", new Icon(VaadinIcon.CART));
        addToCartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addToCartButton.setWidth("100%");
        addToCartButton.setHeight("45px");
        
        addToCartButton.addClickListener(event -> {
            int selectedQuantity = quantityField.getValue();

            if (selectedSize == null) {
                Notification.show("Seleziona una taglia prima di aggiungere al carrello", 3000, Notification.Position.MIDDLE);
            } else if (selectedQuantity < 1) {
                Notification.show("Seleziona una quantità valida", 3000, Notification.Position.MIDDLE);
            } else if (selectedQuantity > MAX_QUANTITY) {
                Notification.show("Puoi acquistare massimo " + MAX_QUANTITY + " unità di questo prodotto", 3000, Notification.Position.MIDDLE);
            } else {
                try {
                    // Recupera il prodotto selezionato
                    p = dbManager.returnProductByModel(model, selectedSize, selectedQuantity);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (p == null) {
                    Notification.show("Non possediamo " + selectedQuantity + " prodotti da lei selezionati in magazzino!", 3000, Notification.Position.MIDDLE);
                } else {
                    Cart.addItem(p, selectedQuantity); // Aggiunge il prodotto al carrello
                    Notification.show("Prodotto aggiunto al carrello!", 3000, Notification.Position.MIDDLE);
                    int availability = Math.min(MAX_QUANTITY, sizeAvailability.get(selectedSize));                  
                    sizeAvailability.put(selectedSize, availability);
                    refreshSizeButtons(sizeAvailability, sizeLayout, quantityField); // Aggiorna i pulsanti delle taglie
                } 
            }    	 	
        });

        // Bottone per tornare al catalogo
        Button buttonSecondary = new Button("Torna al catalogo", event -> {
            getUI().ifPresent(ui -> ui.navigate("category-detail"));
        });
        buttonSecondary.setWidth("100%");
        buttonSecondary.setHeight("45px");
        buttonSecondary.getStyle().set("margin-bottom", "30px");

        // Configurazione stili e layout principali
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        
        layoutColumn.addClassName(Padding.XSMALL);
        layoutColumn.setHeightFull();
        layoutColumn.setWidth("410px");
        layoutColumn.setHeight("100%");
        layoutColumn.getStyle().set("flex-grow", "1");
        layoutColumn.getStyle().set("display", "flex");
        layoutColumn.getStyle().set("flex-direction", "column");
        layoutColumn.getStyle().set("overflow", "auto");

        add(layoutRow);
        image.getStyle().set("position", "sticky");
        image.getStyle().set("top", "0");
        image.getStyle().set("height", "84vh");
        layoutRow.add(image);
        layoutRow.add(layoutColumn);
        layoutColumn.add(h2);
        layoutColumn.add(h3);
        layoutColumn.add(selectSizeLabel);
        layoutColumn.add(sizeLayout);
        layoutColumn.add(selectQuantityLabel);
        layoutColumn.add(quantityLayout);
        layoutColumn.add(addToCartButton);
        layoutColumn.add(buttonSecondary);
        layoutColumn.add(sezione1);
        layoutColumn.add(textMedium);
        layoutColumn.add(detail);
        layoutColumn.add(sezione2);
        layoutColumn.add(shipping);
        
        layoutRow.expand(layoutColumn);
    }

    // Metodo per aggiornare i pulsanti delle taglie
    private void refreshSizeButtons(Map<String, Integer> sizeAvailability, HorizontalLayout sizeLayout, IntegerField quantityField) {
        sizeLayout.removeAll(); // Rimuove i pulsanti precedenti

        for (String size : sizes) {
            int availableQuantity = sizeAvailability.get(size) - Cart.getCartItemByModel(model, size);

            Button sizeButton = new Button(size);
            sizeButton.setWidth("80px");
            sizeButton.setHeight("40px");

            if (availableQuantity > 0) {
                sizeButton.addClickListener(event -> {
                    selectedSize = size; // Imposta la taglia selezionata

                    if (previouslySelectedButton != null) {
                        previouslySelectedButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    }

                    sizeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    previouslySelectedButton = sizeButton;

                    max = Math.min(MAX_QUANTITY - Cart.getCartItemByModel(model, selectedSize), availableQuantity); // Aggiorna la quantità massima disponibile
                    quantityField.setMax(max);
                    quantityField.setValue(0);
                });
            } else {
                // Disattiva il pulsante se non ci sono quantità disponibili
                sizeButton.getStyle().set("background-color", "#e0e0e0");
                sizeButton.getStyle().set("color", "#b0b0b0");
                sizeButton.setEnabled(false);
            }
             // Imposta la quantità a zero
            quantityField.setValue(0);
            sizeLayout.add(sizeButton);
        }
    }
}
