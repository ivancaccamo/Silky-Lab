package com.example.progetto.GUI.imagegallery;

import com.example.progetto.backend.CurrentUser;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class ImageGalleryViewCard extends ListItem {

    public ImageGalleryViewCard(String text, String url) {
        addClassNames("image-card");

        Div clickableDiv = new Div();
        clickableDiv.addClassNames(Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE, Width.FULL);
        clickableDiv.getStyle().set("background", "transparent").set("cursor", "pointer")
                .set("transition", "all 0.3s ease-in-out");
            
        // Effetto hover per evidenziare la card
        clickableDiv.getStyle().set("box-shadow", "0px 4px 10px rgba(0, 0, 0, 0.1)");
        clickableDiv.getStyle().set("border", "4px solid transparent");
        clickableDiv.getElement().addEventListener("mouseover", e -> clickableDiv.getStyle()
                .set("box-shadow", "0px 4px 15px rgba(0, 0, 0, 0.2)")
                .set("border", "4px solid #ccc"));
        clickableDiv.getElement().addEventListener("mouseout", e -> clickableDiv.getStyle()
                .set("box-shadow", "0px 2px 10px rgba(0, 0, 0, 0.1)")
                .set("border", "4px solid transparent"));
        
        clickableDiv.getStyle().set("max-height", "350px"); // Altezza massima della card
        clickableDiv.getStyle().set("overflow", "hidden"); // Nasconde il contenuto in eccesso
        
        clickableDiv.addClickListener(event -> {
        	CurrentUser.setCategoryView(text);
            getUI().ifPresent(ui -> {
                ui.navigate("category-detail");
            });
        });
        
        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.getStyle().set("position", "relative");
        div.getStyle().set("width", "100%");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(url);
        image.setAlt(text);

        image.getStyle().set("object-fit", "cover"); // Per far sì che l'immagine riempia l'area senza distorcersi
        
        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(text);

        clickableDiv.add(div, header);
        add(clickableDiv);
    }
}
