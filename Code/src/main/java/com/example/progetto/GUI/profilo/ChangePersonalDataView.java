package com.example.progetto.GUI.profilo;




import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.CurrentUser;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
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
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.sql.SQLException;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Modifica profilo personale")
@Route("ModificaDati")

public class ChangePersonalDataView extends Composite<VerticalLayout> {
	
	
	private User user = CurrentUser.getUser();
    public ChangePersonalDataView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        EmailField emailField = new EmailField();
        PasswordField passwordField = new PasswordField();
        Button buttonPrimary = new Button("Applica",event ->{
        	user.setName(textField.getValue()); // Nome
            user.setSurname(textField2.getValue()); // Cognome
            user.setEmail(emailField.getValue()); // Email
            user.setPassword(passwordField.getValue()); // Password    
        	try {             
                DatabaseManager dbManager = new DatabaseManager();
                dbManager.updateUser(user); // Metodo per inserire l'utente nel database
                Notification.show("Modifiche avvenute con successo");
            } catch (SQLException e) {
                Notification.show("Errore");
                e.printStackTrace();
            }
        });
        Hr hr = new Hr();
        H1 h1 = new H1();
        
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();
        TextField textField5 = new TextField();
        Button buttonPrimary2 = new Button();
        if (user!=null) {
        	textField.setValue(user.getName());
        	textField2.setValue(user.getSurname());
        	emailField.setValue(user.getEmail());
        	passwordField.setValue(user.getPassword());
        	
        }
        
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        h1.setText("Modifica i tuoi dati");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        textField.setLabel("Nome");
        textField.setWidth("min-content");
        textField2.setLabel("Cognome");
        textField2.setWidth("min-content");
        emailField.setLabel("Email");
        emailField.setWidth("min-content");
        passwordField.setLabel("Password");
        passwordField.setWidth("min-content");
        buttonPrimary.setText("Applica");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.END, buttonPrimary);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        textField3.setLabel("Indirizzo");
        textField3.setWidth("min-content");
        textField4.setLabel("CittÃ ");
        textField4.setWidth("min-content");
        textField5.setLabel("Cap");
        textField5.setWidth("min-content");
        buttonPrimary2.setText("Applica");
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.END, buttonPrimary2);
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutRow);
        layoutRow.add(layoutRow2);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(h1);
        layoutColumn2.add(textField);
        layoutColumn2.add(textField2);
        layoutColumn2.add(emailField);
        layoutColumn2.add(passwordField);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(hr);
        layoutColumn2.add(textField3);
        layoutColumn2.add(textField4);
        layoutColumn2.add(textField5);
        layoutColumn2.add(buttonPrimary2);
    }
}