package com.example.progetto.GUI.areaprivata;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.vaadin.lineawesome.LineAwesomeIconUrl;



@PageTitle("Registrazione")
@Route("Registrazione")

public class RegistrationView extends Composite<VerticalLayout> {
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
    public RegistrationView() {
    	Notification error = new Notification("Errore durante la registrazione controlla di avere inserito dati validi",2000);
    	Notification emptyField = new Notification("Riempire tutti i campi per completare la registrazione",2000);
    	Notification success = new Notification("Registrazione avvenuta con successo",2000);
    	error.setClassName("custom-notification");
    	emptyField.setClassName("custom-notification");
    	success.setClassName("custom-notification");
    	ComboBox<String> countrySelect = new ComboBox<>("Country");
        countrySelect.setRequiredIndicatorVisible(true);
        countrySelect.setItems(countries);
        countrySelect.setValue("Italy");
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();
        Paragraph note = new Paragraph();
        FormLayout formLayout2Col = new FormLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        TextField textField = new TextField();
        textField.setRequiredIndicatorVisible(true);
        TextField textField2 = new TextField();
        textField2.setRequiredIndicatorVisible(true);
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        EmailField emailField = new EmailField();
        emailField.setRequiredIndicatorVisible(true);
        PasswordField passwordField = new PasswordField();
        passwordField.setRequiredIndicatorVisible(true);
        HorizontalLayout layoutRow6 = new HorizontalLayout();
        HorizontalLayout layoutRow7 = new HorizontalLayout();
        HorizontalLayout layoutRow8 = new HorizontalLayout();
        TextField textField4 = new TextField();
        textField4.setRequiredIndicatorVisible(true);
        TextField textField5 = new TextField();
        textField5.setRequiredIndicatorVisible(true);
        HorizontalLayout layoutRow9 = new HorizontalLayout();
        HorizontalLayout layoutRow10 = new HorizontalLayout();
        TextField capField = new TextField();
        capField.setPattern("[0-9]{5}");
        capField.setMaxLength(5);
        capField.setMinLength(5);
        capField.setRequiredIndicatorVisible(true);
      
        Button buttonPrimary = new Button("Registrati", event -> {
        	if(!textField.getValue().isEmpty()&&!textField.getValue().isEmpty()&&!textField2.getValue().isEmpty()&&!emailField.getValue().isEmpty()&&!textField4.getValue().isEmpty()&&!textField5.getValue().isEmpty()&&!countrySelect.getValue().isEmpty()) {
        		 if(!emailField.getValue().contains(" ")&&!capField.isInvalid()&&!emailField.isInvalid()) {
        			 User user = new User();
                     Address address = new Address();
                     user.setName(textField.getValue()); // Nome
                     user.setSurname(textField2.getValue()); // Cognome
                     user.setEmail(emailField.getValue()); // Email
                     user.setPassword(passwordField.getValue()); // Password
                     user.setRole("CLIENT"); // Ruolo predefinito
                     address.setAddress(textField4.getValue());
                     address.setCity(textField5.getValue());
                     address.setCap(capField.getValue());
                     address.setCountry(countrySelect.getValue());
                     
                     try {
                         DatabaseManager dbManager = new DatabaseManager();
                         dbManager.saveUser(user);
                         User savedUser = dbManager.findUserByEmail(user.getEmail().toLowerCase());
                         Current.setUser(savedUser);
                         if (savedUser == null) {
                         	throw new RuntimeException("Errore: l'utente non è stato salvato correttamente.");
                         }

                         dbManager.saveAddress(address, savedUser.getId());
                        
                         UI.getCurrent().access(() -> {
                    		 UI.getCurrent().getPage().executeJs("window.location.href = $0", "Profile");
                         });
                         
                     } catch (SQLException e) {
                         error.open();
                         e.printStackTrace();
                     }
        		 }else {
        			 Notification.show("Dati non validi");
        		 }
                
        	}else
        		emptyField.open();
        	
        	
        });
        VerticalLayout layoutColumn4 = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h2.setText("Registrazione");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, h2);
        h2.setWidth("175px");
        note.setText("Tutti i campi sono obbligatori");
        formLayout2Col.setWidth("100%");
        layoutRow2.setHeightFull();
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);
        textField.setLabel("Nome");
        textField.setWidth("min-content");
        textField.setHeight("80px");
        textField2.setLabel("Cognome");
        textField2.setWidth("min-content");
        textField2.setHeight("80px");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.setHeight("100px");
        layoutRow4.setHeightFull();
        layoutRow3.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.setHeight("100px");
        layoutRow4.setAlignItems(Alignment.CENTER);
        layoutRow4.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutRow5.setHeightFull();
        layoutRow4.setFlexGrow(1.0, layoutRow5);
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setWidth("100%");
        layoutRow5.getStyle().set("flex-grow", "1");
        layoutRow5.setAlignItems(Alignment.CENTER);
        layoutRow5.setJustifyContentMode(JustifyContentMode.CENTER);
        emailField.setLabel("Email");
        layoutRow5.setAlignSelf(FlexComponent.Alignment.START, emailField);
        emailField.setWidth("min-content");
        emailField.setHeight("60px");
        passwordField.setLabel("Password");
        passwordField.setWidth("min-content");
        passwordField.setHeight("80px");
        layoutRow6.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow6);
        layoutRow6.addClassName(Gap.MEDIUM);
        layoutRow6.setWidth("100%");
        layoutRow6.setHeight("100px");
        layoutRow7.setHeightFull();
        layoutRow6.setFlexGrow(1.0, layoutRow7);
        layoutRow7.addClassName(Gap.MEDIUM);
        layoutRow7.setWidth("100%");
        layoutRow7.setHeight("100px");
        layoutRow7.setAlignItems(Alignment.CENTER);
        layoutRow7.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutRow8.setHeightFull();
        layoutRow7.setFlexGrow(1.0, layoutRow8);
        layoutRow8.addClassName(Gap.MEDIUM);
        layoutRow8.setWidth("100%");
        layoutRow8.getStyle().set("flex-grow", "1");
        layoutRow8.setAlignItems(Alignment.CENTER);
        layoutRow8.setJustifyContentMode(JustifyContentMode.CENTER);
        textField4.setLabel("Indirizzo");
        textField4.setWidth("min-content");
        textField4.setHeight("60px");
        textField5.setLabel("Città");
        textField5.setWidth("min-content");
        textField5.setHeight("60px");
        layoutRow9.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow9);
        layoutRow9.addClassName(Gap.MEDIUM);
        layoutRow9.setWidth("100%");
        layoutRow9.setHeight("100px");
        layoutRow9.setAlignItems(Alignment.CENTER);
        layoutRow9.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutRow10.setHeightFull();
        layoutRow9.setFlexGrow(1.0, layoutRow10);
        layoutRow10.addClassName(Gap.MEDIUM);
        layoutRow10.setWidth("100%");
        layoutRow10.getStyle().set("flex-grow", "1");
        layoutRow10.setAlignItems(Alignment.CENTER);
        layoutRow10.setJustifyContentMode(JustifyContentMode.CENTER);
        capField.setLabel("Cap");
     	layoutRow10.setAlignSelf(FlexComponent.Alignment.CENTER, capField);
     	capField.setWidth("min-content");
        countrySelect.setLabel("Nazione");
        countrySelect.setWidth("min-content");
        buttonPrimary.setText("Registra");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.setWidth("180px");
        buttonPrimary.setHeight("40px");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.getStyle().set("cursor", "pointer");
        layoutColumn4.getStyle().set("flex-grow", "1");
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn3);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(h2, note);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(layoutRow2);
        layoutRow2.add(textField);
        layoutRow2.add(textField2);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(layoutRow4);
        layoutRow4.add(layoutRow5);
        layoutRow5.add(emailField);
        layoutRow5.add(passwordField);
        layoutColumn2.add(layoutRow6);
        layoutRow6.add(layoutRow7);
        layoutRow7.add(layoutRow8);
        layoutRow8.add(textField4);
        layoutRow8.add(textField5);
        layoutColumn2.add(layoutRow9);
        layoutRow9.add(layoutRow10);
        layoutRow10.add(capField);
        layoutRow10.add(countrySelect);
        layoutColumn2.add(buttonPrimary);
        layoutRow.add(layoutColumn4);
    }
}