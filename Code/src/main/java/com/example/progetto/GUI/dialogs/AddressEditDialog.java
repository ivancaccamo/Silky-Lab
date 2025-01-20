package com.example.progetto.GUI.dialogs;

import java.sql.SQLException;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Address;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AddressEditDialog extends Dialog {

    public AddressEditDialog(Address address,DatabaseManager db) {
        // Campi del form
        TextField addressField = new TextField("Indirizzo");
        addressField.setValue(address.getAddress());

        TextField countryField = new TextField("Paese");
        countryField.setValue(address.getCountry());

        TextField cityField = new TextField("Città");
        cityField.setValue(address.getCity());

        TextField capField = new TextField("CAP");
        capField.setValue(String.valueOf(address.getCap()));

        // Layout del form
        FormLayout formLayout = new FormLayout();
        formLayout.add(addressField, countryField, cityField, capField);

        // Pulsanti
        Button cancelButton = new Button("Annulla", event -> close());
        Button saveButton = new Button("Conferma", event -> {
            address.setAddress(addressField.getValue());
            address.setCountry(countryField.getValue());
            address.setCity(cityField.getValue());
            address.setCap(Integer.parseInt(capField.getValue()));
            

            try {
				db.updateAddressById(address);
				getUI().ifPresent(ui -> ui.getPage().reload());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            close();
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, saveButton);

        // Layout generale
        VerticalLayout dialogLayout = new VerticalLayout(formLayout, buttonLayout);
        add(dialogLayout);

        // Configura il dialogo
        setWidth("500px");
        setHeight("600px");
    }

}
