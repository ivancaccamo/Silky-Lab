package com.example.progetto.GUI.areaprivata;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.sql.SQLException;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Registrazione")
@Route("Registrazione")
public class RegistrationView extends Composite<VerticalLayout> {

    public RegistrationView () {
        H1 h1 = new H1("Registrazione");
        HorizontalLayout layoutRow = new HorizontalLayout();
        TextField textField = new TextField();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        TextField textField2 = new TextField();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        TextField textField3 = new TextField();
        EmailField emailField = new EmailField();
        PasswordField passwordField = new PasswordField();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        Button buttonPrimary = new Button("salva", event -> {
            // Creazione di un nuovo utente
            User utente = new User();
            utente.setName(textField.getValue()); // Nome
            utente.setSurname(textField2.getValue()); // Cognome
            utente.setEmail(emailField.getValue()); // Email
            utente.setPassword(passwordField.getValue()); // Password
            utente.setRole("CLIENT"); // Ruolo predefinito
            

            try {
                // Aggiungi l'utente al database
                DatabaseManager dbManager = new DatabaseManager();
                dbManager.saveUser(utente); // Metodo per inserire l'utente nel database
                Notification.show("Registrazione completata!");
                getUI().ifPresent(ui -> ui.navigate("Profile"));
                getUI().ifPresent(ui -> ui.getPage().reload());
            } catch (SQLException e) {
                Notification.show("Errore durante la registrazione.");
                e.printStackTrace();
            }
        });
        Button buttonSecondary = new Button();
        Paragraph textMedium = new Paragraph();
        RouterLink routerLink = new RouterLink();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        h1.setWidth("max-content");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        textField.setLabel("Nome");
        textField.setWidth("min-content");
        layoutRow2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        textField2.setLabel("Cognome");
        textField2.setWidth("min-content");
        layoutRow3.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        textField3.setLabel("Numero di telefono");
        textField3.setWidth("min-content");
        emailField.setLabel("Email");
        emailField.setWidth("min-content");
        passwordField.setLabel("Password");
        passwordField.setWidth("min-content");
        layoutRow4.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.getStyle().set("flex-grow", "1");
    
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Cancella");
        buttonSecondary.setWidth("min-content");
        textMedium.setText("Sei già registrato? Fai il");
        textMedium.getStyle().set("flex-grow", "1");
        textMedium.setMinWidth("44px");
        textMedium.setMaxWidth("300px");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        routerLink.setText("Login");
        routerLink.setRoute(LoginView.class);
        layoutRow4.setAlignSelf(FlexComponent.Alignment.START, routerLink);
        getContent().add(h1);
        getContent().add(layoutRow);
        layoutRow.add(textField);
        layoutRow.add(layoutRow2);
        layoutRow2.add(textField2);
        getContent().add(layoutRow3);
        layoutRow3.add(emailField); 
        layoutRow3.add(passwordField);
        getContent().add(layoutRow4);
        layoutRow4.add(buttonPrimary);
        layoutRow4.add(buttonSecondary);
        layoutRow4.add(textMedium);
        layoutRow4.add(routerLink);
    }
}
