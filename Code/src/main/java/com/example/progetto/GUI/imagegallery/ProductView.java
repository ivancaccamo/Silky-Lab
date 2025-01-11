package com.example.progetto.GUI.imagegallery;

import com.example.progetto.backend.CurrentUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Prodotto")
@Route("product-detail")
public class ProductView extends VerticalLayout{
	
	private String product;

    public ProductView() {
        // Recupera il prodotto selezionata da CurrentUser
        product = CurrentUser.getProductView();

        if (product == null || product.isEmpty()) {
            product = "Prodotto non trovato";
        }

        // Layout
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        // Titolo
        H2 header = new H2(product);
        header.getStyle().set("margin-bottom", "10px");
        layout.add(header);
        
     // Bottone per tornare indietro
        Button backButton = new Button("Torna indietro", new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("category-detail"));
        });
        
        layout.add(header, backButton);
        add(layout);
    }
}
