package com.example.progetto.GUI.dialogs;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Model;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.IntegerField;

public class QuantityManagerDialog extends Dialog {
 private List<String> sizes = Arrays.asList("S", "M", "L", "XL");
 private Map<String, IntegerField> sizeQuantityFields = new HashMap<>();
    public QuantityManagerDialog(String productName, Model model) throws SQLException {
        // Titolo del dialog
        H4 title = new H4("Disponibilità " + productName);
       
        // Layout principale
        DatabaseManager db = new DatabaseManager();
        VerticalLayout layout = new VerticalLayout();
        layout.add(title);
        for(String size:sizes) {
        	 IntegerField quantityField = new IntegerField();
        	sizeQuantityFields.put(size, quantityField);
        	int availableQuantity = db.countRecords(model, size);
        	HorizontalLayout row = new HorizontalLayout();
        	row.setAlignSelf(Alignment.CENTER);
        	Div sizeBox = new Div();
        	
        	Span sizeLetter = new Span(size);
        	sizeLetter.getStyle().set("font-weight", "bold");
        	sizeBox.add(sizeLetter);
        	sizeBox.getStyle().set("display", "flex"); // Imposta il layout Flexbox
            sizeBox.getStyle().set("flex-direction", "column"); // Allinea in verticale
            sizeBox.getStyle().set("justify-content", "center"); // Centra verticalmente
            sizeBox.getStyle().set("align-items", "left"); // Centra orizzontalmente
        	row.add(sizeBox);
        	row.setJustifyContentMode(JustifyContentMode.BETWEEN);
        	row.getStyle().set("padding", "20px");
        	row.setWidthFull();
        	row.setHeight("50px");
        	sizeBox.setWidth("30%");
        	
        	quantityField.setValue(availableQuantity);
            // Valore iniziale
            quantityField.setMin(0); // Quantità minima
             
            quantityField.setStep(1); // Incremento
            quantityField.setWidth("80px");
            
            // Pulsanti per aumentare/diminuire la quantità
            Button decreaseButton = new Button(new Icon(VaadinIcon.MINUS), event -> {
                int currentValue = quantityField.getValue();
                if (currentValue > 0) {
                    quantityField.setValue(currentValue - 1);
                }
            });

            Button increaseButton = new Button(new Icon(VaadinIcon.PLUS), event -> {
                int currentValue = quantityField.getValue();
                    quantityField.setValue(currentValue + 1);
                
            });
            increaseButton.getStyle().set("cursor", "pointer");
            decreaseButton.getStyle().set("cursor", "pointer");

            HorizontalLayout quantityLayout = new HorizontalLayout(decreaseButton, quantityField, increaseButton);
            quantityLayout.getStyle().set("align-items", "center");
            quantityLayout.setSizeUndefined();
            
            row.add(quantityLayout);
           
          
           layout.getStyle().set("padding", "10px"); // Spazio interno al bordo
            layout.add(row);
        }
        layout.setAlignItems(Alignment.CENTER);
        // Pulsante "Modifica"
        
        Button cancelButton = new Button("Annulla", event -> {
        	
            close();
        });
        
        // Pulsante "Annulla"
        Button modifyButton = new Button("Salva", event -> {
        	// Raccogli i dati delle quantità
            sizeQuantityFields.forEach((size, field) -> {
                int quantity = field.getValue();
                
                try {
					db.updateQuantity(model, size, quantity);
					System.out.println("Taglia: " + size + ", Quantità aggiornata: " + quantity);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Esempio di funzione di aggiornamento
            close();
            });
            });
        modifyButton.getStyle().set("cursor", "pointer");
        cancelButton.getStyle().set("cursor", "pointer");
        cancelButton.setWidth("130px");
        modifyButton.setWidth("130px");
        modifyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // Layout per i pulsanti
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton , modifyButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttonLayout.setSpacing(true);
        buttonLayout.getStyle().set("margin-top","15px");
        

        add(layout, buttonLayout);
        

        setWidth("400px");
        setHeight("450px");
    }

}
