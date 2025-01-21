package com.example.progetto.GUI.areaprivata;

import com.example.application.services.DatabaseManager;
import com.example.progetto.GUI.MainLayout;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import java.sql.SQLException;

@PageTitle("Log in area")
@Route(value="Login",layout = MainLayout.class)
public class LoginView extends Composite<VerticalLayout> {
private User user = Current.getCurrentUser();	
    public LoginView() {
    	if(user==null) {
    	
        HorizontalLayout layoutRow = new HorizontalLayout();
        H1 h1 = new H1();
        Hr hr = new Hr();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        EmailField emailField = new EmailField();
        PasswordField passwordField = new PasswordField();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Button buttonPrimary = new Button("Login", event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();

            try {
                DatabaseManager dbManager = new DatabaseManager();
                User user = dbManager.findUserByEmail(email);
                if (user != null && user.getPassword().equals(password)) {	
                	Current.setUser(user);
                	 UI.getCurrent().access(() -> {
                         MainLayout.getInstance().updateFooter();
                     });         	
                    if ("ADMIN".equals(user.getRole())) {
                    	
                    	
                        getUI().ifPresent(ui -> ui.navigate(""));
                        
                    } else if ("CLIENT".equals(user.getRole())) { 
                    	
                    	
                        getUI().ifPresent(ui -> ui.navigate(""));
                        
                    }
                } else {
                    Notification.show("Credenziali non valide");
                }
            } catch (SQLException e) {
                Notification.show("Errore di connessione al database");
                e.printStackTrace();
            }
        });
        Button buttonSecondary = new Button("Login", event -> {
        	 getUI().ifPresent(ui -> ui.navigate("Registrazione"));
        });
        VerticalLayout layoutColumn4 = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("100px");
        layoutRow.setAlignItems(Alignment.START);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        h1.setText("Per accedere a tutte le funzionalità del sito esegui l'accesso");
        layoutRow.setAlignSelf(FlexComponent.Alignment.END, h1);
        h1.setWidth("max-content");
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn3.setWidth("300px");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        h3.setText("Log in");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, h3);
        h3.setWidth("max-content");
        emailField.setLabel("Email");
        emailField.setWidth("250px");
        passwordField.setLabel("Password");
        passwordField.setWidth("250px");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.addClassName(Padding.LARGE);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        layoutRow3.setAlignItems(Alignment.START);
        layoutRow3.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonPrimary.setText("Accesso");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Registrazione");
        buttonSecondary.setWidth("min-content");
        layoutColumn4.setWidth("300px");
        layoutColumn4.getStyle().set("flex-grow", "1");
        getContent().add(layoutRow);
        layoutRow.add(h1);
        getContent().add(hr);
        getContent().add(layoutRow2);
        layoutRow2.add(layoutColumn3);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(emailField);
        layoutColumn2.add(passwordField);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(buttonPrimary);
        layoutRow3.add(buttonSecondary);
        layoutRow2.add(layoutColumn4);
    	}else {
    		Button goBack = new Button("Chiudi");
            goBack.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            goBack.addClickListener(event -> {
                getUI().ifPresent(ui -> {
                    ui.navigate("");
                }); 
            });
    		getContent().setAlignItems(Alignment.CENTER);
            getContent().add(new H2("Ciao " + user.getName() + ", sei già loggato"), goBack);
    	}
 	}
}