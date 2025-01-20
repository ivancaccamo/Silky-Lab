package com.example.progetto.GUI.recaporder;

import com.example.progetto.GUI.CartView;
import com.example.progetto.GUI.checkoutform.CheckoutFormView;
import com.example.progetto.backend.Cart;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import java.text.DecimalFormat;

@PageTitle("Riepilogo Ordine")
@Route("Riepilogo-ordine")
public class RecapOrderView extends VerticalLayout {

    public RecapOrderView() {
        addClassName("order-summary-view");
        
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setWidthFull();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSpacing(true);  // Aggiunge uno spazio tra le colonne
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.setWidth("80%");

        // Creazione della UI di riepilogo
        H2 header = new H2("Riepilogo Ordine");
        
        VerticalLayout recapOrderLayout = new VerticalLayout();

        // Dettagli dell'ordine
        Span message1 = new Span("Il pagamento è stato completato con successo!");
        message1.getElement().getStyle().set("color", "green");
        message1.getStyle().set("font-weight", "bold");

        // Mostra gli articoli del carrello
        Span message2 = new Span("Articoli acquistati: ");
        H4 cartItems = new H4();
        cartItems.add(CartView.getCartItems()); // Recupera gli articoli dal carrello

        // Totale dell'ordine
        DecimalFormat df = new DecimalFormat("0.00");
        float total = CartView.getTotal();
        float totalWithDelivery = total < 100 ? total + 7.9f : total;

        H3 totalPrice = new H3("Totale: " + df.format(totalWithDelivery) + " €");
        totalPrice.getStyle().set("font-weight", "bold");

        // Bottone per tornare al carrello o alla home
        Button goBack = new Button("Chiudi");
        goBack.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        goBack.addClickListener(event -> {
            getUI().ifPresent(ui -> {
                ui.navigate("");
            });
        });
        
        // Dettagli cliente
        VerticalLayout customerInfoLayout = new VerticalLayout();
        customerInfoLayout.addClassNames(Background.CONTRAST_5, BoxSizing.BORDER, Padding.LARGE, BorderRadius.LARGE);
        
        Span nameSpan1 = new Span("Cliente: ");
        Span emailSpan1 = new Span("Email: ");
        Span countrySpan1 = new Span("Paese: ");
        Span addressSpan1 = new Span("Indirizzo: ");
        Span citySpan1 = new Span("Città: ");
        
        Span nameSpan2 = new Span(CheckoutFormView.getName() + " " + CheckoutFormView.getSurname());
        Span emailSpan2 = new Span(CheckoutFormView.getEmail());
        Span countrySpan2 = new Span(CheckoutFormView.getCountry());
        Span addressSpan2 = new Span(CheckoutFormView.getAddress());
        Span citySpan2 = new Span(CheckoutFormView.getPostalCode() + " " + CheckoutFormView.getCity());
        
        nameSpan1.getStyle().set("font-weight", "bold");
        emailSpan1.getStyle().set("font-weight", "bold");
        countrySpan1.getStyle().set("font-weight", "bold");
        addressSpan1.getStyle().set("font-weight", "bold");
        citySpan1.getStyle().set("font-weight", "bold");
        
        HorizontalLayout nameLayout = new HorizontalLayout();
        HorizontalLayout emailLayout = new HorizontalLayout();
        HorizontalLayout countryLayout = new HorizontalLayout();
        HorizontalLayout addressLayout = new HorizontalLayout();
        HorizontalLayout cityLayout = new HorizontalLayout();
        
        nameLayout.add(nameSpan1, nameSpan2);
        emailLayout.add(emailSpan1, emailSpan2);
        countryLayout.add(countrySpan1, countrySpan2);
        addressLayout.add(addressSpan1, addressSpan2);
        cityLayout.add(citySpan1, citySpan2);
        
        recapOrderLayout.getStyle().set("width", "50%");
        customerInfoLayout.getStyle().set("width", "50%");
        recapOrderLayout.setAlignItems(Alignment.START);
        customerInfoLayout.setAlignItems(Alignment.START);
        
        add(header, mainLayout);
        mainLayout.add(recapOrderLayout, customerInfoLayout);
        recapOrderLayout.add(message1, message2, cartItems);
        
        if (total < 100) {
        	H5 shipping = new H5("+ Spedizione: 7,90 €");
        	recapOrderLayout.add(shipping);
        }
        
        recapOrderLayout.add(totalPrice, goBack);
        customerInfoLayout.add(nameLayout, emailLayout, countryLayout, addressLayout, cityLayout);
        
        Cart.removeAll();
    }
}