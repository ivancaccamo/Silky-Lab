package com.example.progetto.GUI.summaryorder;

import com.example.progetto.GUI.CartView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.DecimalFormat;

@PageTitle("Riepilogo Ordine")
@Route("Riepilogo-ordine")
public class SummaryOrderView extends VerticalLayout {

    public SummaryOrderView() {
        addClassName("order-summary-view");

        // Creazione della UI di riepilogo
        H2 header = new H2("Riepilogo Ordine");

        // Dettagli dell'ordine (questi dovrebbero essere recuperati dopo il pagamento)
        Span message = new Span("Il pagamento è stato completato con successo!");
        message.getElement().getStyle().set("color", "green");

        // Mostra gli articoli del carrello
        H4 cartItems = new H4("Articoli nel carrello: ");
        cartItems.add(CartView.getCartItems()); // Recupera gli articoli dal carrello

        // Totale dell'ordine
        DecimalFormat df = new DecimalFormat("0.00");
        float total = CartView.getTotal();
        float totalWithDelivery = total < 100 ? total + 7.9f : total;

        Span totalPrice = new Span("Totale: €" + df.format(totalWithDelivery));

        // Bottone per tornare al carrello o alla home
        Button goBack = new Button("Torna alla home");
        goBack.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        goBack.addClickListener(event -> {
            getUI().ifPresent(ui -> {
                ui.navigate("home");
            });
        });

        // Aggiungi i componenti alla view
        add(header, message, cartItems, totalPrice, goBack);
    }
}