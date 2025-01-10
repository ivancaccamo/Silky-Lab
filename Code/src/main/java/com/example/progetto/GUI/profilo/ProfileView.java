package com.example.progetto.GUI.profilo;

import com.example.progetto.backend.CurrentUser;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Profilo personale")
@Route("Profile")
public class ProfileView extends Composite<VerticalLayout> {
private User user = CurrentUser.getUser();
    public ProfileView() {
    	
    	if(user != null) {
    		 VerticalLayout layoutColumn2 = new VerticalLayout();
    	H1 h1 = new H1();	 
        H3 h3 = new H3();
        H3 h32 = new H3();
        H3 h33 = new H3();
        H3 h34 = new H3();
        Hr hr = new Hr();
        Hr hr2 = new Hr();
        H3 h35 = new H3();
        H3 h36 = new H3();
        H3 h37 = new H3();
        Button buttonPrimary1 = new Button("Modifica",event ->{
        	getUI().ifPresent(ui -> ui.navigate("ModificaDati"));
        	
        });
        Button buttonPrimary2 = new Button("Modifica",event -> {
        	getUI().ifPresent(ui -> ui.navigate("ModificaDati"));
        });
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h1.setText("Profilo personale");
        h3.setText("Nome: "+user.getName());
        h3.setWidth("max-content");
        h32.setText("Cognome: "+user.getSurname());
        h32.setWidth("max-content");
        h34.setText("Email: "+user.getEmail());
        h34.setWidth("max-content");
        h35.setText("Indirizzo");
        h35.setWidth("max-content");
        h36.setText("Città");
        h36.setWidth("max-content");
        h37.setText("Cap");
        h37.setWidth("max-content");
        buttonPrimary1.setWidth("min-content");
        buttonPrimary1.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary1.setText("Modifica");
        buttonPrimary2.setWidth("min-content");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.setText("Modifica");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h1);
        layoutColumn2.add(hr2);
        layoutColumn2.add(h3);
        layoutColumn2.add(h32);
        layoutColumn2.add(h34);
        layoutColumn2.add(buttonPrimary1);
        layoutColumn2.add(hr);
        layoutColumn2.add(h35);
        layoutColumn2.add(h36);
        layoutColumn2.add(h37);
        layoutColumn2.add(buttonPrimary2);
    }
  }
       
}
