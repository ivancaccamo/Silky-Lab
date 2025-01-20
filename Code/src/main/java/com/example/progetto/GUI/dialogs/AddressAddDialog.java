package com.example.progetto.GUI.dialogs;

import java.sql.SQLException;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Address;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;

public class AddressAddDialog extends Dialog {

    public AddressAddDialog(DatabaseManager db,int idUser) {
        // Campi del form
        TextField addressField = new TextField("Indirizzo");
        TextField countryField = new TextField("Paese");
        TextField cityField = new TextField("Città");
        TextField capField = new TextField("CAP");

        // Layout del form
        FormLayout formLayout = new FormLayout();
        formLayout.add(addressField, countryField, cityField, capField);

        // Pulsanti
        Button cancelButton = new Button("Annulla", event -> close());
        Button saveButton = new Button("Aggiungi", event -> {
            Address newAddress = new Address();
            newAddress.setAddress(addressField.getValue());
            newAddress.setCountry(countryField.getValue());
            newAddress.setCity(cityField.getValue());
            newAddress.setCap(Integer.parseInt(capField.getValue()));
            
            try {
				db.saveAddress(newAddress, idUser);
				getUI().ifPresent(ui -> ui.getPage().reload()); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        // Layout generale
        VerticalLayout dialogLayout = new VerticalLayout(formLayout, buttonLayout);
        add(dialogLayout);

        // Configura il dialogo
        setWidth("500px");
        setHeight("500px");
    }

    
}
