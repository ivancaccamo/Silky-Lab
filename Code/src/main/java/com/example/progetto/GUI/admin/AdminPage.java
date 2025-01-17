package com.example.progetto.GUI.admin;
import com.example.application.services.DatabaseManager;
import com.example.progetto.backend.Model;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Route("admins")
public class AdminPage extends VerticalLayout {
	
	private final String IMAGE_PATH = "src//main//resources//META-INF//resources//Images//";
    private ComboBox<Model> productComboBox = new ComboBox<>("Select Product");
    private Image productImage = new Image("/Images/Image_not_found.png","");
    private Button saveButton = new Button("Carica immagine");
    
    

    public AdminPage() {
    	productImage.setWidth("100%");
    	
    	productImage.getStyle().set("object-fit", "cover"); // Per far sì che l'immagine riempia l'area senza distorcersi
        
        // Carica i prodotti dal database
        List<Model> models = loadProducts();
        productComboBox.setItems(models);
        productComboBox.setItemLabelGenerator(model -> model.getName());
        VerticalLayout verticalBox = new VerticalLayout();
    
        // Configurazione upload
        FileBuffer fileBuffer = new FileBuffer();
        Upload upload = new Upload(fileBuffer);
        upload.addSucceededListener(event -> {
            Notification.show("File uploaded: " + event.getFileName());
            
        });
        
        productImage.setHeight("100%");
        productImage.setWidth("100%");
        upload.setWidth("100%");
        verticalBox.add(productImage);
        verticalBox.add(upload);
        verticalBox.setClassName("upload-image");
        
        // Configurazione bottone salva
        saveButton.addClickListener(e -> saveImage(productComboBox.getValue(), fileBuffer));
        
     
        add(productComboBox, verticalBox, saveButton);
    }
    
    private List<Model> loadProducts() {
        List<Model> products = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Model");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Model model = new Model(
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("imageUrl")
                );
                products.add(model);
            }
        } catch (SQLException e) {
        	
            Notification.show("Error loading products: " + e.getMessage());
        }
        return products;
    }

    private void saveImage(Model model, FileBuffer buffer) {
        if (model == null) {
            Notification.show("Please select a product.");
            return;
        }

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
            productImage.setSrc("/Images/"+model.getName()+".png");
        } catch (IOException e) {
            Notification.show("Error saving image: " + e.getMessage());
        }
    }

}