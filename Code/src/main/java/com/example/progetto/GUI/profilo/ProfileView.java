package com.example.progetto.GUI.profilo;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.CurrentUser;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;

@PageTitle("Profilo")
@Route("Profile")
public class ProfileView extends VerticalLayout {

    private User user= CurrentUser.getUser();
    
    public ProfileView() {
    	
    	

       if(user != null) {
    	   TextField nameField = new TextField("Nome");
        nameField.setValue(user.getName());

        TextField surnameField = new TextField("Cognome");
        surnameField.setValue(user.getSurname());

        EmailField emailField = new EmailField("Email");
        emailField.setValue(user.getEmail());

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setValue(user.getPassword());

        // Bottone per salvare le modifiche
        Button saveButton = new Button("Salva modifiche", event -> {
        	user.setName(nameField.getValue());
        	user.setSurname(surnameField.getValue());
        	user.setEmail(emailField.getValue());
            user.setPassword(passwordField.getValue());

            try {
                DatabaseManager dbManager = new DatabaseManager();
                dbManager.updateUser(user);// Metodo per aggiornare i dati nel database
                getUI().ifPresent(ui -> ui.getPage().reload());
                Notification.show("Dati aggiornati con successo!");
            } catch (SQLException e) {
                Notification.show("Errore durante l'aggiornamento dei dati.");
                e.printStackTrace();
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Layout
        add(nameField, surnameField, emailField, passwordField, saveButton);
    }else {
    	
    }
       }
        
}
