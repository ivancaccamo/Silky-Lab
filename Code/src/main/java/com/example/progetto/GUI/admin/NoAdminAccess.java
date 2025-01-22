package com.example.progetto.GUI.admin;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

public class NoAdminAccess extends VerticalLayout{
	
    public void noAccess() {
    	HorizontalLayout layoutRow = new HorizontalLayout();
        H1 h12 = new H1();
        
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn8 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        Paragraph textMedium2 = new Paragraph();
        Image image = new Image("/Images/warning.png","");
        VerticalLayout layoutColumn9 = new VerticalLayout();
        HorizontalLayout layoutRow5 = new HorizontalLayout();
        setWidth("100%");
        getStyle().set("flex-grow", "1");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        h12.setText("Impossibile accedere alla pagina");
        h12.setWidth("max-content");
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn8.getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn3.setAlignSelf(Alignment.CENTER, image);
        layoutColumn3.setAlignSelf(Alignment.CENTER, textMedium2);
        image.setHeight("300px");
        image.setWidth("300px");
        textMedium2.setText("Attenzione! Stai cercando di accedere ad una pagina che richiede un permesso che non possiedi");
        
        textMedium2.setWidth("max-content");
        textMedium2.getStyle().set("font-size", "var(--lumo-font-size-m)");
        layoutColumn9.getStyle().set("flex-grow", "1");
        layoutRow5.addClassName(Gap.MEDIUM);
        layoutRow5.setWidth("100%");
        layoutRow5.setHeight("min-content");
        add(layoutRow);
        layoutRow.add(h12);
        add(layoutRow2);
        layoutRow2.add(layoutColumn8);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(image);
        layoutColumn3.add(textMedium2);
        
        layoutRow2.add(layoutColumn9);
        add(layoutRow5);
    }
}
