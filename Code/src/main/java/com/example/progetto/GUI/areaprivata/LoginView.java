package com.example.progetto.GUI.areaprivata;

import java.sql.SQLException;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.CurrentUser;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout {

    /**
	 * 
	 */
	

	public LoginView() {
    	
        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login", event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();

            try {
                DatabaseManager dbManager = new DatabaseManager();
                User user = dbManager.findUserByEmail(email);
                if (user != null && user.getPassword().equals(password)) {
                	Notification.show("Credenziali valide");
                	CurrentUser.setUser(user);
                	 getUI().ifPresent(ui -> ui.navigate("Profile"));
                    if ("ADMIN".equals(user.getRole())) {
                        getUI().ifPresent(ui -> ui.navigate("admin-home"));
                    } else if ("CLIENT".equals(user.getRole())) { 
                    	
                        getUI().ifPresent(ui -> ui.navigate("Profile"));
                       
                    }
                } else {
                    Notification.show("Credenziali non valide");
                }
            } catch (SQLException e) {
                Notification.show("Errore di connessione al database");
                e.printStackTrace();
            }
        });

        add(emailField, passwordField, loginButton);
    }
}
