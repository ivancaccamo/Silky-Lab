package com.example.progetto.GUI.home;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.progetto.GUI.areaprivata.AreaPrivataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.HOME_SOLID)
public class HomeView extends Composite<VerticalLayout> {

    public HomeView () {
        Image logo = new Image("Images/SilkyLabIcon.png", "");
        getContent().setAlignItems(Alignment.CENTER); 
        getContent().add(logo);
        
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        Paragraph textMedium = new Paragraph();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h3.setText("About us");
        h3.setWidth("max-content");
        h3.getStyle().set("margin-left", "auto");
        h3.getStyle().set("margin-right", "auto");
        textMedium.setText(
                "Il progetto Silky Lab nasce con l’obiettivo di creare un e-commerce dedicato all’abbigliamento, che si distingue per l’attenzione alla qualità dei prodotti e un’esperienza utente semplice e intuitiva. L’interfaccia utente rappresenta una aspetto centrale dell’esperienza di Silky Lab, garantendo un design chiaro, minimale e facilmente navigabile, in linea con la filosofia del brand. I capi offerti rifletteranno gli stessi concetti: semplicità e qualità.");
        textMedium.setWidth("70%");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        textMedium.getStyle().set("margin-left", "200px");
        textMedium.getStyle().set("margin-right", "200px");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(textMedium);
    }
}