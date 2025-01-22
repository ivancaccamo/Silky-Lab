package com.example.progetto.GUI.dialogs;

import java.util.ArrayList;

import com.example.progetto.backend.Address;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;

public class AddressChooseDialog extends Dialog{
	
	public AddressChooseDialog(ArrayList<Address> addresses, Consumer<Address> onAddressSelected){
		setWidth("470px"); // Larghezza del dialog
        setHeight("500px"); // Altezza del dialog

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		VerticalLayout addressList = new VerticalLayout();
		addressList.setHeight("300px");
		addressList.getStyle().set("flex-grow", "1");
		addressList.getStyle().set("display", "flex");
		addressList.getStyle().set("flex-direction", "column");
		addressList.getStyle().set("overflow", "auto");

        // Crea un'area cliccabile per ogni indirizzo
        for (Address address : addresses) {
            Div addressItem = createAddressItem(address);
            addressItem.addClickListener(event -> {
                // Imposta l'indirizzo selezionato
            	onAddressSelected.accept(address);
            	close();
            	//getUI().ifPresent(ui -> ui.getPage().reload());
            });
            addressList.add(addressItem);
        }
		
		// Bottoni per confermare o annullare
		
		Button cancelButton = new Button("Annulla", event -> {
			close();
		});
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.getStyle().set("cursor", "pointer");
		
        HorizontalLayout footer = new HorizontalLayout(cancelButton);
        footer.setJustifyContentMode(JustifyContentMode.END);
        
        Span select = new Span("Seleziona un indirizzo:");
        select.getStyle().set("font-weight", "bold");
        
        layout.add(select, addressList, footer);
        add(layout);
	}
	
	private Div createAddressItem(Address address) {
        Div addressItem = new Div();
        addressItem.setClassName("address");
        Span addressLabel = new Span(address.getAddress());
        Span countryLabel = new Span(address.getCountry());
        Span cityLabel = new Span(address.getCity() + " " + address.getCap());
        VerticalLayout addressDetails = new VerticalLayout(countryLabel, cityLabel, addressLabel);
        addressDetails.getStyle().set("gap", "2px");
        addressItem.add(addressDetails);
        addressItem.getStyle().set("cursor", "pointer");
        addressItem.getElement().addEventListener("mouseover", e -> addressItem.getStyle()
                .set("box-shadow", "0px 4px 15px rgba(0, 0, 0, 0.2)")
                .set("border", "4px solid #ccc"));
        addressItem.getElement().addEventListener("mouseout", e -> addressItem.getStyle()
                .set("box-shadow", "0px 2px 10px rgba(0, 0, 0, 0.1)")
                .set("border", "4px solid transparent"));
        return addressItem;
    }
}
