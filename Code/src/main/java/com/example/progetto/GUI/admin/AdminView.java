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
            Current.setCategoryView("prova");
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
        
    	}else {
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
            textMedium2.setText("Attenzione! Stai cercando di accedere ad una pagina che richiede un rango che non possiedi");
            
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

