package com.example.progetto.GUI.admin;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Model;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Admin")
@Route("admin")
@Menu(order = 0, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
public class AdminView extends Composite<VerticalLayout> {

    public AdminView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        TextField textField = new TextField();
        NumberField numberField = new NumberField();
        TextArea textArea = new TextArea();
        Paragraph textMedium = new Paragraph();
        FileBuffer fileBuffer = new FileBuffer();
        Upload upload = new Upload(fileBuffer);
        upload.addSucceededListener(event -> {
            Notification.show("File uploaded: " + event.getFileName());
            
        });
        Button buttonPrimary = new Button("Carica prodotti",event -> {
        	{
            	if(textField.getValue()!=null||numberField.getValue()!=null||textArea.getValue()!=null) {
            		
                    Model model = new Model();
                    try {
                        DatabaseManager dbManager = new DatabaseManager();
                        model.setName(textField.getValue());
                        model.setPrice(numberField.getValue());
                        model.setCategory(Current.getCategoryView());
                        model.setDescription(textArea.getValue());                        
                        dbManager.saveModel(model); 
                        Notification.show("Modello aggiunto con successo!");
                        getUI().ifPresent(ui -> ui.navigate("category-detail"));   
                    } catch (SQLException e) {                  
                        e.printStackTrace();
                        Notification.show("Qualcosa è andato storto");
                    }
            	}else
            		Notification.show("Riempi tutti i campi per proseguire");
        	}
        });
       
        getContent().setWidth("100%");
        getContent().setHeight("705px");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setHeight("600px");
        textField.setLabel("Nome");
        textField.setWidth("min-content");
        numberField.setLabel("Prezzo");
        numberField.setWidth("min-content");
        numberField.setHeight("90px");
        textArea.setLabel("Descrizione");
        textArea.setWidth("500px");
        textArea.setHeight("100px");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        textMedium.setText(
                "Di default vengono inseriti: per la taglia XL 5 capi, per la taglia L 10 capi, per la taglia M 20 capi, per la taglia S 20 capi, per la taglia XS 10 capi");
        textMedium.setWidth("100%");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        getContent().add(layoutColumn2);
        layoutColumn2.add(textField);
        layoutColumn2.add(numberField);
        layoutColumn2.add(textArea);
        layoutColumn2.add(upload);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(textMedium);
        
    }
}