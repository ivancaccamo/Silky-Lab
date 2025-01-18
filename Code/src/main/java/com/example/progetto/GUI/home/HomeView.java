package com.example.progetto.GUI.home;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.HOME_SOLID)
public class HomeView extends Composite<VerticalLayout> {
	 
    public HomeView () {
        Image logo = new Image("Images/SilkyLabIcon.png", "");
        logo.setWidth("660px");
        logo.setHeight("auto"); // Proporzioni originali
        getContent().setAlignItems(Alignment.CENTER); 
        getContent().add(logo);
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();
        Paragraph textMedium = new Paragraph();
        textMedium.getStyle().set("text-align", "center");
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("50%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h2.setText("About us");
        h2.setWidth("max-content");
        h2.getStyle().set("margin-left", "auto");
        h2.getStyle().set("margin-right", "auto");
        textMedium.setText(
                "Il progetto Silky Lab nasce con l’obiettivo di creare un e-commerce dedicato all’abbigliamento, che si distingue per l’attenzione alla qualità dei prodotti e un’esperienza utente semplice e intuitiva. L’interfaccia utente rappresenta una aspetto centrale dell’esperienza di Silky Lab, garantendo un design chiaro, minimale e facilmente navigabile, in linea con la filosofia del brand. I capi offerti rifletteranno gli stessi concetti: semplicità e qualità.");
        textMedium.setWidth("70%");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        textMedium.getStyle().set("margin-left", "90px");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h2);
        layoutColumn2.add(textMedium);   
        
        // Footer con Contatti
        Footer footer = new Footer();
        
        footer.getStyle().set("font-weight", "bold");
        footer.getStyle().set("width", "100%");
        footer.getStyle().set("background-color", "grey");
        footer.getStyle().set("color", "white");
        footer.getStyle().set("text-align", "center");
        footer.getStyle().set("margin-top", "auto"); // Fa in modo che il footer rimanga sempre in basso
        
        HorizontalLayout emailLayout = new HorizontalLayout();
        Paragraph contactInfo = new Paragraph("Contattaci: info@silkylab.com |");  
        Paragraph email1 = new Paragraph("m.brozzoni4@studenti.unibg.it");
        Paragraph email2 = new Paragraph("i.caccamo@studenti.unibg.it");
        Paragraph email3 = new Paragraph("s.preda2@studenti.unibg.it");
        
        emailLayout.setWidthFull(); // Imposta la larghezza completa
        emailLayout.setAlignItems(Alignment.CENTER); // Centra verticalmente
        emailLayout.setJustifyContentMode(JustifyContentMode.CENTER); // Centra orizzontalmente
        
        contactInfo.getStyle().set("margin-right", "20px");
        email1.getStyle().set("margin-right", "20px");
        email2.getStyle().set("margin-right", "20px");
        
        emailLayout.add(contactInfo, email1, email2, email3);

        footer.add(emailLayout);
        getContent().add(footer);
    }
}