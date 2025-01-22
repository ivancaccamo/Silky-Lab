	package com.example.progetto.GUI.admin;

    import com.example.application.services.DatabaseManager;
import com.example.progetto.ProjectApplication;
import com.example.progetto.backend.Cart;
    import com.example.progetto.backend.Current;
    import com.example.progetto.backend.Model;
    import com.example.progetto.backend.Product;
import com.example.progetto.backend.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.vaadin.flow.component.button.Button;
    import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
    import com.vaadin.flow.component.html.H3;
    import com.vaadin.flow.component.html.H4;
    import com.vaadin.flow.component.html.H5;
    import com.vaadin.flow.component.html.Image;
    import com.vaadin.flow.component.html.Paragraph;
    import com.vaadin.flow.component.icon.Icon;
    import com.vaadin.flow.component.icon.VaadinIcon;
    import com.vaadin.flow.component.notification.Notification;
    import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
    import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.AbstractNumberField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.PageTitle;
    import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.hilla.ApplicationContextProvider;

    @PageTitle("Admin")
    @Route("admin")
    public class AdminView extends VerticalLayout {

            
    	private final String IMAGE_PATH = "src//main//resources//META-INF//resources//Images//";
        
        DatabaseManager dbManager = new DatabaseManager(); // Gestore del database
        

        public AdminView() { 	
        	if(Current.getCurrentUser()==null||Current.getCurrentUser().getRole().equals("CLIENT")) {
        	NoAdminAccess no = new NoAdminAccess();
        	no.noAccess();
        	add(no);
    	}else {
    		
        	H1 h1 = new H1();	
        	TextField textField = new TextField("Inserisci il nome del prodotto");
            TextArea textArea = new TextArea("Descrizione dettagliata");
            NumberField numberField = new NumberField("Prezzo modello");
            FileBuffer fileBuffer = new FileBuffer();
            Upload upload = new Upload(fileBuffer);
            Image image = new Image("/Images/Image_not_found.png","");
            upload.addSucceededListener(event -> {
            	InputStream inputStream = fileBuffer.getInputStream();
                Notification.show("File uploaded: " + event.getFileName());  
                StreamResource resource = new StreamResource(event.getFileName(), () -> inputStream);
                image.setSrc(resource);
                
            });
            HorizontalLayout layoutRow = new HorizontalLayout();
            VerticalLayout layoutColumn = new VerticalLayout();       
            // Bottone per aggiungere i modelli dal database
            Button addButton = new Button("Aggiugni modello");
            addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            addButton.setWidth("100%");
            addButton.setHeight("45px");
            addButton.addClickListener(event -> {
            	
				if(textField.getValue()!=null&&numberField.getValue()!=null&&textArea.getValue()!=null) {
        		
                Model model = new Model();
                try {
                    DatabaseManager dbManager = new DatabaseManager();
                    model.setName(textField.getValue());
                    model.setPrice(numberField.getValue());
                    model.setCategory(Current.getCategoryView());
                    model.setDescription(textArea.getValue());        
                    dbManager.saveModel(model);                      
                    dbManager.loadProducts(dbManager.returnIdModel(model));       
                    Notification.show("Modello aggiunto con successo!");
                    getUI().ifPresent(ui -> ui.navigate("category-detail"));   
                } catch (SQLException e) {                  
                    e.printStackTrace();
                    Notification.show("Qualcosa è andato storto");
				
				}
                
                saveImage(model,fileBuffer);
                
        	}else {
        		Notification.show("Riempi tutti i campi per proseguire");
        	}	
    	}  
         );  		
            // Bottone per tornare al catalogo
            Button buttonSecondary = new Button("Torna alle categorie", event -> {
                getUI().ifPresent(ui -> ui.navigate("category-detail"));
            });
            buttonSecondary.setWidth("100%");
            buttonSecondary.setHeight("45px");
            buttonSecondary.getStyle().set("margin-bottom", "30px");

            // Configurazione stili e layout principali
            setJustifyContentMode(JustifyContentMode.CENTER);
            setAlignItems(Alignment.CENTER);           
            layoutColumn.addClassName(Padding.XSMALL);
            layoutColumn.setHeightFull();
            layoutColumn.setWidth("410px");
            layoutColumn.setHeight("100%");
            layoutColumn.getStyle().set("flex-grow", "1");
            layoutColumn.getStyle().set("display", "flex");
            layoutColumn.getStyle().set("flex-direction", "column");
            layoutColumn.getStyle().set("overflow", "auto"); 
            if(Current.getCategoryView()!=null)
            h1.setText("Aggiungi un modello di prodotto nella categoria "+Current.getCategoryView());
            add(h1);
            add(layoutRow);
            image.getStyle().set("position", "sticky");
            image.setClassName("product-image");    
            layoutRow.getStyle().set("margin", "50px");
            layoutRow.add(image);
            layoutRow.add(layoutColumn);
            textField.setWidth("100%");
            numberField.setWidth("100%");
            textArea.setWidth("100%");
            upload.setWidth("100%");     
            layoutColumn.add(textField);
            layoutColumn.add(numberField);
            layoutColumn.add(textArea);
            layoutColumn.add(upload);
            layoutColumn.add(addButton);
            layoutColumn.add(buttonSecondary);  
            layoutRow.expand(layoutColumn);
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

