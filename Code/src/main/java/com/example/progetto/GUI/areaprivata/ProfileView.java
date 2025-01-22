package com.example.progetto.GUI.areaprivata;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.application.services.DatabaseManager;
import com.example.progetto.GUI.MainLayout;
import com.example.progetto.GUI.dialogs.AddressAddDialog;
import com.example.progetto.GUI.dialogs.AddressEditDialog;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Order;
import com.example.progetto.backend.SoldProduct;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "Profile", layout = MainLayout.class)
@PageTitle("Profilo Personale")
@Menu(order = 3, icon = LineAwesomeIconUrl.ADDRESS_CARD_SOLID)
public class ProfileView extends VerticalLayout {
    private ArrayList<Address> addresses = new ArrayList<>();
    static DatabaseManager dbManager = new DatabaseManager();
    private User user = Current.getCurrentUser();
    private ArrayList<Order> orders = new ArrayList<>();
    
    public ProfileView() throws SQLException {
    	
    	if (user == null) {
    		Button goLogin = new Button("Login");
            goLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            goLogin.getStyle().set("cursor", "pointer");
            goLogin.addClickListener(event -> {
                getUI().ifPresent(ui -> {
                    ui.navigate("Login");
                }); 
            });
    		setAlignItems(Alignment.CENTER);
            add(new H2("Effettua l'accesso per continuare"), goLogin);
            return;
        }
        // Header con l'icona dell'account e il nome
    	orders= dbManager.getOrdersByUserId(user.getId());
        HorizontalLayout header = new HorizontalLayout();
        Image accountIcon = new Image("/images/account-icon.png", "Account Icon");
        accountIcon.setWidth("100px");
        accountIcon.setHeight("100px");

        Span userName = new Span(user.getName() + " " + user.getSurname());
        userName.getStyle().set("font-size", "24px").set("font-weight", "bold");
        Button disconnectButton = new Button("Disconnetti");
        disconnectButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        disconnectButton.getStyle().set("cursor", "pointer");
        disconnectButton.addClickListener(event -> {
        	Current.ExitUser();
            Notification.show("Disconnesso con successo");
            UI.getCurrent().access(() -> {
            	UI.getCurrent().getPage().executeJs("window.location.href = $0", "");
            });
            getUI().ifPresent(ui -> {
                ui.navigate("");
            }); 
            
            
        });
        VerticalLayout verL = new VerticalLayout();
        verL.add(userName);
        verL.add(disconnectButton);

        header.add(accountIcon, verL);
        header.setSpacing(true);
        header.setAlignItems(Alignment.CENTER);

        // Tabs per le sezioni
        Tab personalDataTab = new Tab("Dati Personali");
        Tab addressesTab = new Tab("Indirizzi di Spedizione");
        Tab ordersTab = new Tab("Ordini Effettuati");
        personalDataTab.getStyle().set("cursor", "pointer");
        addressesTab.getStyle().set("cursor", "pointer");
        ordersTab.getStyle().set("cursor", "pointer");

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
                	H3 h3 = new H3();
                	
                	h3.setText("I tuoi acquisti: ");
                	content.add(h3);
                	if(orders.isEmpty()) {
                		Span emptyList = new Span("Noi hai mai effettuato ordini da noi, continua ad esplorare il nostro sito");
                		content.add(emptyList);
                	}
                	Collections.reverse(orders);
                	for(Order order : orders) {
                		content.add(createOrderDetailLayout(order));
                		Hr hr = new Hr();
                		content.add(hr);
                	}
                    
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
                getUI().ifPresent(ui -> ui.getPage().reload());
            } catch (SQLException e) {
                Notification.show("Errore");
                e.printStackTrace();
            }
        });;
        editButton.getStyle().set("margin-top", "20px");
        editButton.getStyle().set("cursor", "pointer");

        FormLayout form = new FormLayout(nameField, surnameField, emailField, passwordField, editButton);
        layout.add(form);

        return layout;
    }

    private VerticalLayout createAddressesSection() throws SQLException {
    	AddressAddDialog dialog = new AddressAddDialog(dbManager,Current.getCurrentUser().getId());
        VerticalLayout layout = new VerticalLayout();
        Span addressesLabel = new Span("Elenco Indirizzi:");
        
        HorizontalLayout addressList = new HorizontalLayout();
        addresses = dbManager.getAddressesByUserId(user.getId());
        for (Address address : addresses) {
            addressList.add(createAddressItem(address));
        }
        
        Button addButton = new Button("Aggiungi Indirizzo", VaadinIcon.PLUS.create(),event ->{
        	dialog.open();        	       	  	
        });
        addButton.getStyle().set("cursor", "pointer");
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
        	
        });
        deleteButton.getStyle().set("cursor", "pointer");
        editButton.getStyle().set("cursor", "pointer");
        Hr hr = new Hr();
        deleteButton.addClickListener(event -> addressItem.getParent().ifPresent(parent -> {
        	try {
				dbManager.deleteAddressById(address.getID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (parent instanceof HorizontalLayout) {
                ((HorizontalLayout) parent).remove(addressItem);
            }
        }));

        HorizontalLayout buttonLayout = new HorizontalLayout(editButton, deleteButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

        VerticalLayout addressDetails = new VerticalLayout(countryLabel, cityLabel, addressLabel,hr,buttonLayout);
        addressDetails.getStyle().set("gap", "2px");
        addressItem.add(addressDetails);

        return addressItem;
    }

    public static HorizontalLayout createOrderDetailLayout(Order order) throws SQLException {
        // Sezione sinistra: dettagli dell'ordine
    	HorizontalLayout orderBox = new HorizontalLayout();
    	ArrayList<SoldProduct> soldProducts = new ArrayList<>();
    	soldProducts = dbManager.getSoldProductsByOrderId(order.getID());
        VerticalLayout orderDetailsLayout = new VerticalLayout();
        Span idOrder = new Span("ID Ordine: #" + order.getID());
        idOrder.getStyle().set("font-weight", "bold");
        orderDetailsLayout.add(idOrder);
        DecimalFormat df = new DecimalFormat("0.00");
        String total = df.format(order.getTot());
        orderDetailsLayout.add(new Span("Totale: " + total + "€"));
        orderDetailsLayout.add(new Span("Data: " + order.getDate()));

        // Sezione destra: prodotti venduti
        VerticalLayout productsLayout = new VerticalLayout();
        for (SoldProduct product : soldProducts) {
            Div productDiv = new Div();
            productDiv.add(new Span(product.getModel() + ", Taglia: " + product.getSize()));
            productDiv.getStyle().set("padding", "1px");
            productDiv.getStyle().set("align-self", "flex-start");
            productsLayout.add(productDiv);
            
        }
        productsLayout.getStyle()
        .set("gap", "1px")           // Spazio tra gli elementi
        .set("padding", "1px")      // Margine interno
        .set("margin", "1px");  

        // Scroller per la sezione dei prodotti
        Scroller productScroller = new Scroller();
        productScroller.setContent(productsLayout);
        productScroller.setHeight("100px");
        productScroller.setWidth("100%");// Altezza fissa per lo scroller
        productScroller.getStyle()
        .set("overflow-x", "hidden");
        VerticalLayout vertical = new VerticalLayout();
        Span span = new Span("Prodotti: ");
        span.getStyle().set("font-weight", "bold");
        vertical.add(span);
        vertical.add(productScroller);
        vertical.getStyle().set("gap", "2px");
        // Aggiungi i layout al contenitore principale
        
        
        orderBox.add(orderDetailsLayout, vertical);
        
        // Stile e allineamento
        
        orderBox.setPadding(true);
        orderBox.getStyle().set("gap", "1px");   
        orderBox.setWidthFull();
        orderBox.setWidth("700px");
        orderDetailsLayout.setWidth("30%");
        vertical.setWidth("70%");
		return orderBox;
    }
}
