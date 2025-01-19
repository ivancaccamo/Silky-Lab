package com.example.progetto.GUI.imagegallery;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Product;
import com.example.progetto.backend.Model;


@PageTitle("Categoria")
@Route("category-detail")
public class CategoryView extends VerticalLayout {

    private String category;

    public CategoryView() {
        // Recupera la categoria selezionata da CurrentUser
        category = Current.getCategoryView();

        if (category == null || category.isEmpty()) {
            category = "Categoria non trovata";
        }

        // Layout
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        // Titolo
        H2 header = new H2(category);
        header.getStyle().set("margin-bottom", "10px");
        layout.add(header);
        
        // Bottone per tornare indietro
        Button backButton = new Button("Torna indietro", new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addClickListener(event -> {
        	
            getUI().ifPresent(ui -> ui.navigate("Shop"));
        });

        DatabaseManager dbManager = new DatabaseManager();
        ArrayList<Model> models = new ArrayList<>();

        try {
			models = dbManager.returnModelsOnCategory(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Contenitore griglia prodotti
        FlexLayout grid = new FlexLayout();

        grid.getStyle().set("display", "grid");
        grid.getStyle().set("grid-template-columns", "repeat(3, 0fr)");
        grid.setWidth("100%");
        grid.setWidthFull();
        grid.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        grid.setJustifyContentMode(FlexLayout.JustifyContentMode.CENTER);
        grid.setAlignItems(FlexLayout.Alignment.CENTER);
        grid.getStyle().set("gap", "70px"); 

        // Aggiungi ogni prodotto alla griglia
        for (Model model : models) {
             ProductCard productCard = new ProductCard(model);
             grid.add(productCard);             
        } 
        if(Current.getUser()==null||Current.getUser().getRole().equals("ADMIN")) {
        	ProductCard productCard = new ProductCard();
        	grid.add(productCard);
        }
        layout.setWidth("100%");
        layout.setAlignSelf(Alignment.CENTER);
        layout.add(backButton, grid); 
        add(layout);
    }
}