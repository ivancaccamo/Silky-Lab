package com.example.progetto.GUI.dialogs;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Address;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;

public class AddressAddDialog extends Dialog {
	private static final Set<String> countries = new LinkedHashSet<>();
	static {
        countries.addAll(Arrays.asList("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola",
                "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia",
                "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
                "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Bouvet Island",
                "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei Darussalam", "Bulgaria",
                "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
                "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands",
                "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus",
                "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador",
                "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands",
                "Faroe Islands", "Federated States of Micronesia", "Fiji", "Finland", "France", "French Guiana",
                "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana",
                "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea",
                "Guinea-Bissau", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong",
                "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast",
                "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
                "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau",
                "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
                "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Moldova", "Monaco", "Mongolia",
                "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands",
                "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue",
                "Norfolk Island", "North Korea", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau",
                "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal",
                "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis",
                "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe",
                "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
                "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands",
                "South Korea", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname",
                "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic",
                "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago",
                "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
                "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands",
                "United States Virgin Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City State", "Venezuela",
                "Vietnam", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zaire", "Zambia",
                "Zimbabwe"));
    }
    public AddressAddDialog(DatabaseManager db,int idUser) {
        // Campi del form
        TextField addressField = new TextField("Indirizzo");
        addressField.setRequiredIndicatorVisible(true);
        ComboBox<String> countryField = new ComboBox<>("Paese");
        countryField.setRequiredIndicatorVisible(true);
        countryField.setItems(countries);
        TextField cityField = new TextField("Città");
        cityField.setRequiredIndicatorVisible(true);
        TextField capField = new TextField("CAP");
        capField.setPattern("[0-9]{5}");
        capField.setMaxLength(5);
        capField.setMinLength(5);
        capField.setRequiredIndicatorVisible(true);
        countryField.setValue("Italy");
        // Layout del form
        FormLayout formLayout = new FormLayout();
        formLayout.add(countryField,addressField,capField,cityField);

        // Pulsanti
        Button cancelButton = new Button("Annulla", event -> close());
        cancelButton.getStyle().set("cursor", "pointer");
        Button saveButton = new Button("Aggiungi", event -> {

        		 Address newAddress = new Address();
        		 newAddress.setAddress(addressField.getValue());
        		 newAddress.setCountry(countryField.getValue());
        		 newAddress.setCity(cityField.getValue());
        		 newAddress.setCap(capField.getValue());
            
            try {
				db.saveAddress(newAddress, idUser);
				getUI().ifPresent(ui -> ui.getPage().reload()); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            close();
        	
        });
        saveButton.setEnabled(false);
        saveButton.getStyle().set("cursor", "default");

        capField.addValueChangeListener(event -> {
            String capValue = capField.getValue();
            if (capValue.matches("[0-9]{5}") && !addressField.isEmpty() && !countryField.isEmpty() && !cityField.isEmpty()) {
                saveButton.setEnabled(true);
                saveButton.getStyle().set("cursor", "pointer");
            } else {
                saveButton.setEnabled(false);
                saveButton.getStyle().set("cursor", "default");
            }
        });
        cityField.addValueChangeListener(event -> {
            String capValue = capField.getValue();
            if (capValue.matches("[0-9]{5}") && !addressField.isEmpty() && !countryField.isEmpty() && !cityField.isEmpty()) {
                saveButton.setEnabled(true);
                saveButton.getStyle().set("cursor", "pointer");
            } else {
                saveButton.setEnabled(false);
                saveButton.getStyle().set("cursor", "default");
            }
        });
        countryField.addValueChangeListener(event -> {
            String capValue = capField.getValue();
            if (capValue.matches("[0-9]{5}") && !addressField.isEmpty() && !countryField.isEmpty() && !cityField.isEmpty()) {
                saveButton.setEnabled(true);
                saveButton.getStyle().set("cursor", "pointer");
            } else {
                saveButton.setEnabled(false);
                saveButton.getStyle().set("cursor", "default");
            }
        });
        addressField.addValueChangeListener(event -> {
            String capValue = capField.getValue();
            if (capValue.matches("[0-9]{5}") && !addressField.isEmpty() && !countryField.isEmpty() && !cityField.isEmpty()) {
                saveButton.setEnabled(true);
                saveButton.getStyle().set("cursor", "pointer");
            } else {
                saveButton.setEnabled(false);
                saveButton.getStyle().set("cursor", "default");
            }
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
