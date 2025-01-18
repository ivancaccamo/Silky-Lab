package com.example.progetto.GUI.admin;

import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Address;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.Model;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.orderedlayout.FlexComponent;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Admin")
@Route("admin")
@Menu(order = 0, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
public class AdminView extends Composite<VerticalLayout> {
	
	
	private final String FlexComponent = null;
	private final String IMAGE_PATH = "src//main//resources//META-INF//resources//Images//";
    public AdminView() {
    	if(Current.getCurrentUser()==null||Current.getCurrentUser().getRole().equals("CLIENT")) {
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
            getContent().setWidth("100%");
            getContent().getStyle().set("flex-grow", "1");
            getContent().setJustifyContentMode(JustifyContentMode.CENTER);
            getContent().setAlignItems(Alignment.CENTER);
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
            textMedium2.setText("Attenzione! Stai cercando di accedere ad una pagina che richiede un rango che non possiedi");
           
            textMedium2.setWidth("max-content");
            textMedium2.getStyle().set("font-size", "var(--lumo-font-size-m)");
            layoutColumn9.getStyle().set("flex-grow", "1");
            layoutRow5.addClassName(Gap.MEDIUM);
            layoutRow5.setWidth("100%");
            layoutRow5.setHeight("min-content");
            getContent().add(layoutRow);
            layoutRow.add(h12);
            getContent().add(layoutRow2);
            layoutRow2.add(layoutColumn8);
            layoutRow2.add(layoutColumn2);
            layoutColumn2.add(layoutColumn3);
            layoutColumn3.add(image);
            layoutColumn3.add(textMedium2);
            
            layoutRow2.add(layoutColumn9);
            getContent().add(layoutRow5);
	    
	}else {
		ComboBox<String> categoryComboBox = new ComboBox<>("Seleziona una categoria");
        categoryComboBox.setItems("Felpe", "Pantaloni", "Magliette", "Accessori");
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
                        model.setCategory(categoryComboBox.getValue()); 
                        dbManager.saveModel(model);                      
                        dbManager.loadProducts(dbManager.returnIdModel(model));       
                        Notification.show("Modello aggiunto con successo!");
                        getUI().ifPresent(ui -> ui.navigate("category-detail"));   
                    } catch (SQLException e) {                  
                        e.printStackTrace();
                        Notification.show("Qualcosa è andato storto");
					
					}
                    
                    saveImage(model,fileBuffer);
                    
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
        layoutColumn2.add(categoryComboBox);
        layoutColumn2.add(textField);
        layoutColumn2.add(numberField);
        layoutColumn2.add(textArea);
        layoutColumn2.add(upload);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(textMedium); 
	}
    }
        
    
    private void saveImage(Model model, FileBuffer buffer) {
        try (InputStream inputStream = buffer.getInputStream()) {
            // Salva il file nella cartella "Images"
            File targetFile = new File(IMAGE_PATH+buffer.getFileName());
            try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                byte[] bufferData = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(bufferData)) != -1) {
                    outputStream.write(bufferData, 0, bytesRead);
                }
            }
            
            Files.move(Path.of(IMAGE_PATH+buffer.getFileName()), Path.of(IMAGE_PATH+model.getName()+".png"), StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException e) {
            Notification.show("Error saving image: " + e.getMessage());
        }
    }
}