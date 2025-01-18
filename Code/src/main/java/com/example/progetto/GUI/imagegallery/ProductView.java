package com.example.progetto.GUI.imagegallery;


import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Cart;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Model;
import com.example.progetto.backend.Product;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

@PageTitle("Prodotto")
@Route("product-detail")
public class ProductView extends VerticalLayout{
	
	private String productName = Current.getProductView();
    private List<String> sizes = Arrays.asList("Small", "Medium", "Large", "Extra large"); // Taglie disponibili
    private static final int MAX_QUANTITY = 10; // Quantità massima acquistabile
    private Product p = new Product();
    private Model model = new Model();
    DatabaseManager dbManager = new DatabaseManager();
    public ProductView() {
    	
    	
    	try {
			model = dbManager.returnModelByName(productName);
           

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Image image = new Image("/Images/"+model.getName()+".png","");
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H2 h2 = new H2();
        Hr hr = new Hr();
        Paragraph textMedium = new Paragraph();
        H3 h3 = new H3();
        ComboBox sizeSelector = new ComboBox();
        sizeSelector.setItems(sizes);
        Hr hr2 = new Hr();
        
        Button buttonSecondary = new Button("",event -> {
            getUI().ifPresent(ui -> ui.navigate("category-detail"));
        });
        HorizontalLayout layoutRow3 = new HorizontalLayout(); 
        // Selezione quantità
        IntegerField quantityField = new IntegerField();
        quantityField.setValue(1);
        quantityField.setMin(1);
        quantityField.setMax(MAX_QUANTITY);
        quantityField.setStep(1);
        quantityField.setWidth("80px");
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
            String selectedSize = (String) sizeSelector.getValue();
            int selectedQuantity = quantityField.getValue();
            
            if (selectedSize == null) {
                Notification.show("Seleziona una taglia prima di aggiungere al carrello", 3000, Notification.Position.MIDDLE);
            } else if (selectedQuantity < 1) {
                Notification.show("Seleziona una quantità valida", 3000, Notification.Position.MIDDLE);
            } else if (selectedQuantity > MAX_QUANTITY) {
                Notification.show("Puoi acquistare massimo " + MAX_QUANTITY + " unità di questo prodotto", 3000, Notification.Position.MIDDLE);
            } else {
            	
            	try {	
					p = dbManager.returnProductByModel(model, selectedSize, selectedQuantity);				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	if(p == null) {
            		Notification.show("Non possediamo "+selectedQuantity+" prodotti da lei selezionati in magazzino!", 3000, Notification.Position.MIDDLE);
            	}else {
            		Cart.addItem(p, selectedQuantity);
               // Cart.addItem(p, selectedQuantity);
                Notification.show("Prodotto aggiunto al carrello!", 3000, Notification.Position.MIDDLE);
            	}
            	
            }
        });
        sizeSelector.setPlaceholder("Seleziona la taglia");
        setWidth("100%");
        getStyle().set("flex-grow", "1");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");
        layoutColumn2.addClassName(Padding.LARGE);
        layoutColumn2.setWidth("min-content");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");  
        layoutColumn3.addClassName(Padding.XSMALL); 
        image.setClassName("product-image");
        layoutColumn3.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("300px");
        layoutColumn3.setHeight("500px");
        h2.setText(model.getName());
        h2.setWidth("max-content");
        textMedium.setText(model.getDescription());
        textMedium.setWidth("100%");
        textMedium.setHeight("135px");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        String price = String.valueOf(model.getPrice());
        h3.setText(price+" euro");
        h3.setWidth("max-content");
        sizeSelector.setWidth("100%");
        addToCartButton.setText("Aggiungi al carrello");
        addToCartButton.setWidth("100%");
        addToCartButton.setHeight("45px");
        addToCartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Torna al catalogo");
        buttonSecondary.setWidth("100%");
        buttonSecondary.setHeight("45px");
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("min-content");
        add(layoutRow2);
        add(layoutColumn2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(image);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(h2);
        layoutColumn3.add(textMedium);
        layoutColumn3.add(h3);
        layoutColumn3.add(quantityLayout);
        layoutColumn3.add(sizeSelector);
        layoutColumn3.add(hr2);
        layoutColumn3.add(addToCartButton);
        layoutColumn3.add(buttonSecondary);
        add(layoutRow3);
        
        

        addToCartButton.getStyle().set("margin-bottom", "20px"); 
    }
}
