package com.example.progetto.GUI.imagegallery;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
        DatabaseManager db = new DatabaseManager();

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

        //DatabaseManager dbManager = new DatabaseManager();
        //List<Product> Products = dbManager.returnProductsOnCategory(category);
        ArrayList<Model> models = new ArrayList<>();
        models = 


        // Contenitore griglia prodotti
        FlexLayout grid = new FlexLayout();

        grid.getStyle().set("display", "grid");
        grid.getStyle().set("grid-template-columns", "repeat(3, 1fr)"); // 3 colonne per riga
        grid.getStyle().set("width", "100%");
        grid.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        grid.setJustifyContentMode(FlexLayout.JustifyContentMode.START);
        grid.setAlignItems(FlexLayout.Alignment.START);

        grid.getStyle().set("gap", "30px"); 

        // Aggiungi ogni prodotto alla griglia
        for (Model model : models) {
             ProductCard productCard = new ProductCard(model);
             grid.add(productCard);
        }

        // Aggiungi gli elementi alla View
        layout.add(backButton, grid);
        add(layout);
    }
}