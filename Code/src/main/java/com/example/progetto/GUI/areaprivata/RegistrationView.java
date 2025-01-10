package com.example.progetto.GUI.areaprivata;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.CurrentUser;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import java.util.LinkedHashSet;
import java.util.Set;

import org.vaadin.lineawesome.LineAwesomeIconUrl;



@PageTitle("Registrazione")
@Route("Registrazione")

public class RegistrationView extends Composite<VerticalLayout> {
	private static final Set<String> countries = new LinkedHashSet<>();
    public RegistrationView() {
    	
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();
        FormLayout formLayout2Col = new FormLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        EmailField emailField = new EmailField();
        PasswordField passwordField = new PasswordField();
        HorizontalLayout layoutRow6 = new HorizontalLayout();
        HorizontalLayout layoutRow7 = new HorizontalLayout();
        HorizontalLayout layoutRow8 = new HorizontalLayout();
        TextField textField4 = new TextField();
        TextField textField5 = new TextField();
        HorizontalLayout layoutRow9 = new HorizontalLayout();
        HorizontalLayout layoutRow10 = new HorizontalLayout();
        NumberField numberField = new NumberField();
        numberField.setMin(0); // Valore minimo
        numberField.setMax(99999); // Valore massimo (5 cifre)
        numberField.setStep(1); // Passo incrementale (opzionale)
        TextField textField7 = new TextField();
        Button buttonPrimary = new Button("Registrati", event -> {
        	if(textField.getValue()!=null||textField.getValue()!=null||textField2.getValue()!=null||emailField.getValue()!=null||textField4.getValue()!=null||textField5.getValue()!=null||numberField.getValue()!=null||textField7.getValue()!=null) {
        		 // Creazione di un nuovo utente
                User user = new User();
                Address address = new Address();
                user.setName(textField.getValue()); // Nome
                user.setSurname(textField2.getValue()); // Cognome
                user.setEmail(emailField.getValue()); // Email
                user.setPassword(passwordField.getValue()); // Password
                user.setRole("CLIENT"); // Ruolo predefinito
                address.setAddress(textField4.getValue());
                address.setCity(textField4.getValue());
                int capInt = (int) (numberField.getValue()%numberField.getValue());
                address.setCap(capInt);
                address.setCountry(textField4.getValue());
                try {
                    // Aggiungi l'utente al database
                	CurrentUser.setUser(user);
                    DatabaseManager dbManager = new DatabaseManager();
                    dbManager.saveUser(user); // Metodo per inserire l'utente nel database
                    Notification.show("Registrazione completata!");
                    getUI().ifPresent(ui -> ui.navigate("Profile"));
                    getUI().ifPresent(ui -> ui.getPage().reload());
                } catch (SQLException e) {
                    Notification.show("Errore durante la registrazione.");
                    e.printStackTrace();
                }
        	}else {
        		Notification.show("Devi inserire tutti i dati!");
        	}
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
        textField5.setLabel("CittÃ ");
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
        numberField.setLabel("Cap");
        layoutRow10.setAlignSelf(FlexComponent.Alignment.CENTER, numberField);
        numberField.setWidth("min-content");
        textField7.setLabel("Nazione");
        textField7.setWidth("min-content");
        buttonPrimary.setText("Registra");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.setWidth("180px");
        buttonPrimary.setHeight("40px");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutColumn4.getStyle().set("flex-grow", "1");
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn3);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(h2);
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
        layoutRow10.add(numberField);
        layoutRow10.add(textField7);
        layoutColumn2.add(buttonPrimary);
        layoutRow.add(layoutColumn4);
    }
}


   
       

