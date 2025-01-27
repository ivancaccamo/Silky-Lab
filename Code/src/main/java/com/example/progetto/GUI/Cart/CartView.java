package com.example.progetto.GUI.Cart;

import com.example.progetto.backend.Cart;
import com.example.progetto.backend.CartItem;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.text.DecimalFormat;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Carrello")
@Route("Carrello")
@Menu(order = 2, icon = LineAwesomeIconUrl.SHOPPING_CART_SOLID)
@Uses(Icon.class)
public class CartView extends Composite<VerticalLayout> {
	
	
	static VerticalLayout cartItemsContainer;
	private static float tot = 0;
	private H4 h4 = new H4();
    private H4 h42 = new H4();
    private ProgressBar progressBar = new ProgressBar();
    private H6 h6 = new H6();
    
    public CartView() {
    		cartItemsContainer=null;
    		cartItemsContainer = new VerticalLayout();
    	
    	User currentUser = Current.getCurrentUser();
    	Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setContent(cartItemsContainer);
        scroller.setHeight("200px");
        refresh();
        
        HorizontalLayout layoutRow = new HorizontalLayout();
        H1 h1 = new H1();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        VerticalLayout layoutColumn5 = new VerticalLayout();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        h1.setText("Carrello");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, h1);
        h1.setWidth("max-content");
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn4.setWidth("300px");
        layoutColumn4.getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        h4.setWidth("max-content");
        h42.setWidth("max-content");
        
        h6.setWidth("max-content");     
        scroller.setWidth("100%");
        scroller.getStyle().set("flex-grow", "0");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("CheckOut");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.getStyle().set("flex-grow", "1");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.getStyle().set("cursor", "pointer");
        buttonPrimary.addClickListener(event -> {
            getUI().ifPresent(ui -> {
            	if (currentUser != null) {
	            	if(tot > 0) {
	            		ui.navigate("Checkout-form");
	            	} else {
	            		Notification.show("Aggiungi qualcosa al carrello per procedere al checkout");
	            	}
            	} else {
            		// Naviga alla pagina di login
            		ui.navigate("Login");
            	}
            });
        });
        buttonSecondary.setText("Torna allo shop");
        layoutRow3.setAlignSelf(FlexComponent.Alignment.CENTER, buttonSecondary);
        buttonSecondary.getStyle().set("flex-grow", "1");
        buttonSecondary.getStyle().set("cursor", "pointer");
        buttonSecondary.addClickListener(event -> {
            getUI().ifPresent(ui -> {
                ui.navigate("Shop");
            });
        });
        layoutColumn5.setWidth("300px");
        layoutColumn5.getStyle().set("flex-grow", "1");
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.setWidth("100%");
        layoutRow4.setHeight("min-content");
        getContent().add(layoutRow);
        layoutRow.add(h1);
        getContent().add(layoutRow2);
        layoutRow2.add(layoutColumn4);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(h4);
        layoutColumn3.add(h42);
        layoutColumn3.add(progressBar);
        layoutColumn3.add(h6);
        layoutColumn3.add(scroller);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(buttonPrimary);
        layoutRow3.add(buttonSecondary);
        layoutRow2.add(layoutColumn5);
        getContent().add(layoutRow4);   
    }
    
    private void addCartItem(CartItem item) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setWidthFull();
        
        Span productName = new Span(item.getProduct().getModel().getName());
        Span productSize = new Span(item.getProduct().getSize());
        Span quantity = new Span("x" + item.getQuantity());
        DecimalFormat df = new DecimalFormat("0.00");
        String PruductsPrice = df.format(item.getQuantity() * item.getProduct().getModel().getPrice());
        Span price = new Span(PruductsPrice + " €");
        tot += (float) item.getQuantity() * item.getProduct().getModel().getPrice();
        
        Button deleteButton = new Button("", new Icon(VaadinIcon.TRASH), event -> {
        	tot -= item.getQuantity() * item.getProduct().getModel().getPrice();
        	Cart.removeItem(item); // Rimuove l'elemento dal carrello
            refresh(); // Aggiorna la UI
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        deleteButton.getStyle().set("margin-left", "auto");
        deleteButton.getStyle().set("cursor", "pointer");
        
        layout.add(productName, productSize, quantity, price, deleteButton);
        cartItemsContainer.add(layout);
        cartItemsContainer.setWidth("100%");
    }

    public void refresh() {
    	cartItemsContainer.removeAll();
    	tot=0;
        Cart.getCartItems().forEach(this::addCartItem);
        
     // Aggiorna il totale visualizzato nell'interfaccia
        DecimalFormat df = new DecimalFormat("0.00");
        String total = df.format(tot);
        String totalWithDelivery = df.format(tot + 7.9);

        if (tot == 0) {
            h4.setText("Carrello vuoto");
            h42.setText("");
            h6.setText("");
            progressBar.setValue(0);
        } else if (tot < 100) {
            h4.setText("Totale: " + totalWithDelivery + " €");
            h42.setText("Altri " + df.format(100 - tot) + " € per la spedizione gratuita.");
            h6.setText("Costo di spedizione: 7,90 €");
            progressBar.setValue(tot / 100);
        } else {
            h4.setText("Totale: " + total + " €");
            h42.setText("Hai raggiunto la soglia per la spedizione gratuita!");
            h6.setText("Costo di spedizione: gratis");
            progressBar.setValue(1);
        }
    }

    public static VerticalLayout getCartItems () {
    	 cartItemsContainer.getChildren().forEach(child -> {
    	        // Verifica se l'elemento è un pulsante e se è un deleteButton
    	     child.getChildren().forEach(child2-> {   
    		 if (child2 instanceof Button && ((Button) child2).getText().equals("")) {
    	            // Rimuovi il pulsante di eliminazione
    	            child2.removeFromParent();
    	        }
    	    });
    	 });
    	return cartItemsContainer;  
    }
    
    public static float getTotal() {
    	if(tot >= 100) {
    		return tot;
    	}else {
    		return (float) (tot+7.9);
    	}
        
    }
}