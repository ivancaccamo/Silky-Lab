package com.example.progetto.GUI.areaprivata;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.application.services.DatabaseManager;
import com.example.progetto.GUI.MainLayout;
import com.example.progetto.GUI.dialogs.AddressEditDialog;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "pro", layout = MainLayout.class)
@PageTitle("Profilo Personale")
public class ProfileView extends VerticalLayout {
    private ArrayList<Address> addresses = new ArrayList<>();
    DatabaseManager dbManager = new DatabaseManager();
    private User user = Current.getCurrentUser();

    public ProfileView() {
    	
        // Header con l'icona dell'account e il nome
        HorizontalLayout header = new HorizontalLayout();
        Image accountIcon = new Image("/images/account-icon.png", "Account Icon");
        accountIcon.setWidth("100px");
        accountIcon.setHeight("100px");

        Span userName = new Span(user.getName() + " " + user.getSurname());
        userName.getStyle().set("font-size", "24px").set("font-weight", "bold");

        header.add(accountIcon, userName);
        header.setSpacing(true);
        header.setAlignItems(Alignment.CENTER);

        // Tabs per le sezioni
        Tab personalDataTab = new Tab("Dati Personali");
        Tab addressesTab = new Tab("Indirizzi di Spedizione");
        Tab ordersTab = new Tab("Ordini Effettuati");

        Tabs tabs = new Tabs(personalDataTab, addressesTab, ordersTab);
        tabs.setWidth("100%");
        Div content = new Div();

        tabs.addSelectedChangeListener(event -> {
            content.removeAll();
            try {
                if (event.getSelectedTab().equals(personalDataTab)) {
                    content.add(createPersonalDataSection());
                } else if (event.getSelectedTab().equals(addressesTab)) {
                    content.add(createAddressesSection());
                } else if (event.getSelectedTab().equals(ordersTab)) {
                    content.add(createOrdersSection());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Mostra inizialmente la prima sezione
        content.add(createPersonalDataSection());

        add(header, tabs, content);
    }

    private VerticalLayout createPersonalDataSection() {
        VerticalLayout layout = new VerticalLayout();

        TextField nameField = new TextField("Nome");
        nameField.setValue(user.getName());
        TextField surnameField = new TextField("Cognome");
        surnameField.setValue(user.getSurname());
        TextField emailField = new TextField("Email");
        emailField.setValue(user.getEmail());
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setValue(user.getPassword());

        Button editButton = new Button("Modifica", VaadinIcon.EDIT.create(),event ->{
        	user.setName(nameField.getValue()); // Nome
            user.setSurname(surnameField.getValue()); // Cognome
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
        });;
        editButton.getStyle().set("margin-top", "20px");

        FormLayout form = new FormLayout(nameField, surnameField, emailField, passwordField, editButton);
        layout.add(form);

        return layout;
    }

    private VerticalLayout createAddressesSection() throws SQLException {
        VerticalLayout layout = new VerticalLayout();
        Span addressesLabel = new Span("Elenco Indirizzi:");
        
        VerticalLayout addressList = new VerticalLayout();
        addresses = dbManager.getAddressesByUserId(user.getId());
        for (Address address : addresses) {
            addressList.add(createAddressItem(address));
        }

        Button addButton = new Button("Aggiungi Indirizzo", VaadinIcon.PLUS.create());
        layout.add(addressesLabel, addressList, addButton);

        return layout;
    }

    private Div createAddressItem(Address address) {
        Div addressItem = new Div();
        addressItem.setClassName("address");
        AddressEditDialog editDialog = new AddressEditDialog(address,dbManager);
        Span addressLabel = new Span(address.getAddress());
        Span countryLabel = new Span(address.getCountry());
        Span cityLabel = new Span(address.getCity() + " " + address.getCap());
        Button deleteButton = new Button("Elimina", VaadinIcon.TRASH.create());
        Button editButton = new Button("Modifica", VaadinIcon.PENCIL.create(),event ->{
        	editDialog.open();
        	addressItem.getParent().ifPresent(parent -> {
        		if (parent instanceof VerticalLayout) {
                    ((VerticalLayout) parent).remove(addressItem);
                    ((VerticalLayout) parent).add(addressItem);
                }
        	
        	});
        });
        Hr hr = new Hr();
        deleteButton.addClickListener(event -> addressItem.getParent().ifPresent(parent -> {
        	try {
				dbManager.deleteAddressById(address.getID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (parent instanceof VerticalLayout) {
                ((VerticalLayout) parent).remove(addressItem);
            }
        }));

        HorizontalLayout buttonLayout = new HorizontalLayout(editButton, deleteButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        VerticalLayout addressDetails = new VerticalLayout(countryLabel, cityLabel, addressLabel,hr,buttonLayout);
        addressDetails.getStyle().set("gap", "2px");
        addressItem.add(addressDetails);

        return addressItem;
    }

    private VerticalLayout createOrdersSection() {
        VerticalLayout layout = new VerticalLayout();
        Span ordersLabel = new Span("Riepilogo Ordini:");

        VerticalLayout orderList = new VerticalLayout();
        orderList.add(new Span("Ordine #12345 - 100€ - 10/01/2025"),
                      new Span("Ordine #12346 - 50€ - 15/01/2025"));

        layout.add(ordersLabel, orderList);

        return layout;
    }
}
