package com.example.progetto.GUI.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.application.services.DatabaseManager;
import com.example.progetto.GUI.areaprivata.ProfileView;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Order;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Archivio ordini")
@Route("admin-orders")
@Menu(order = 4, icon = LineAwesomeIconUrl.ARCHIVE_SOLID)
public class AdminOrdersView extends Composite<VerticalLayout> {

    private DatabaseManager dbManager = new DatabaseManager();
    private ArrayList<Order> orders = new ArrayList<>();
    private VerticalLayout layout;

    public AdminOrdersView() {
    	if(Current.getCurrentUser()==null||Current.getCurrentUser().getRole().equals("CLIENT")) {
        	NoAdminAccess no = new NoAdminAccess();
        	no.noAccess();
        	getContent().add(no);
    	} else {
	    	layout = new VerticalLayout();
	        H2 h2 = new H2("Ordini ricevuti: ");
	        // Aggiungi selezione per ordinamento
	        Select<String> orderSelect = new Select<>();
	        orderSelect.setLabel("Ordina per data");
	        orderSelect.setItems("Più recenti", "Meno recenti");
	        orderSelect.setValue("Più recenti"); // Impostazione predefinita
	        orderSelect.addValueChangeListener(event -> updateOrderList(event.getValue()));  // Listener per il cambiamento
	        orderSelect.setWidth("300px"); // Imposta una larghezza per visibilità
	        HorizontalLayout header = new HorizontalLayout(h2, orderSelect);
	        header.setAlignItems(Alignment.CENTER);
	        header.setWidthFull();
	        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
	        header.getStyle().set("padding-left", "50px");
	        header.getStyle().set("padding-right", "50px");
	        getContent().add(header);
	
	        try {
	            orders = dbManager.getOrders();
	            if (orders.isEmpty()) {
	                Span emptyList = new Span("Nessun ordine ricevuto");
	                layout.add(emptyList);
	            } else {
	                updateOrderList("Più recenti");  // Ordinamento predefinito
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	
	        getContent().add(layout);
    	}
    }

    // Metodo per aggiornare la lista degli ordini in base alla selezione
    private void updateOrderList(String sortingOrder) {
        layout.removeAll();  // Rimuovi gli ordini precedenti

        try {
            if (orders.isEmpty()) {
                Span emptyList = new Span("Nessun ordine ricevuto");
                layout.add(emptyList);
            } else {
                // Applica l'ordinamento scelto
                if ("Più recenti".equals(sortingOrder)) {
                    Collections.reverse(orders); 
                } else Collections.reverse(orders); 
                
                // Aggiungi gli ordini
                for (Order order : orders) {
                    HorizontalLayout orderContainer = new HorizontalLayout();
                    orderContainer.add(ProfileView.createOrderDetailLayout(order));
                    Span idClient = new Span("ID Cliente: #" + order.getIDuser());
                    Span email = new Span("Email: #" + dbManager.findUserByID(order.getIDuser()).getEmail());
                    VerticalLayout client = new VerticalLayout (idClient, email); 
                    idClient.getStyle().set("font-weight", "bold");
                    idClient.getStyle().set("margin-top", "15px");
                    orderContainer.add(client); //client
                    layout.add(orderContainer);

                    Hr hr = new Hr();
                    layout.add(hr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
