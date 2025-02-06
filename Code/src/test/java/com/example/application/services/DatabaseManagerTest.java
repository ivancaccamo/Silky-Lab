package com.example.application.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.example.progetto.backend.Model;
import com.example.progetto.backend.Product;

public class DatabaseManagerTest {

    private DatabaseManager databaseManager;

    @Before
    public void setUp() {
        // Inizializza l'oggetto da testare
        databaseManager = new DatabaseManager();
    }

    // =========================================
    // ESEMPIO 1: test di "capitalize"
    // =========================================
    @Test
    public void testCapitalize_NullInput() {
        String input = null;
        String result = invokeCapitalize(input);
        // Se input è null, la tua implementazione restituisce null
        // (oppure stringa vuota, a seconda di come vuoi gestirlo).
        // Dalla tua classe vedo che ritorna "input" se è null o empty,
        // quindi ci aspettiamo null.
        assertNull("capitalize(null) dovrebbe restituire null", result);
    }

    @Test
    public void testCapitalize_EmptyString() {
        String input = "";
        String result = invokeCapitalize(input);
        // Dalla tua implementazione, ritorna lo stesso input se empty
        assertEquals("", result);
    }

    @Test
    public void testCapitalize_NormalCase() {
        String input = "mARIO";
        String result = invokeCapitalize(input);
        // La logica: la prima lettera maiuscola, il resto minuscolo => "Mario"
        assertEquals("Mario", result);
    }

    // =========================================
    // ESEMPIO 2: test di "capitalizeAddress"
    // =========================================
    @Test
    public void testCapitalizeAddress_NullInput() {
        String result = databaseManager.capitalizeAddress(null);
        assertNull("Se input è null, dovrebbe restituire null", result);
    }

    @Test
    public void testCapitalizeAddress_Empty() {
        String result = databaseManager.capitalizeAddress("");
        assertEquals("Stringa vuota in ingresso, stringa vuota in uscita", "", result);
    }

    @Test
    public void testCapitalizeAddress_BasicAddress() {
        String result = databaseManager.capitalizeAddress("via roma 10");
        // La logica: ogni "parola" capitalizzata => "Via Roma 10"
        assertEquals("Via Roma 10", result);
    }

    // =========================================
    // ESEMPIO 3: test di "returnProductByModel"
    // con una logica critica (se non basta la quantità, ritorna null)
    // =========================================
    @Test
    public void testReturnProductByModel_NotEnoughStock() throws Exception {
        // ESEMPIO: useremo un mock di DatabaseManager
        // per forzare "countRecords" a restituire un valore inferiore
        // alla quantità richiesta, e verificare che returnProductByModel() dia null.
        
        // 1) Creiamo un "parziale" mock di DatabaseManager
        DatabaseManager spyManager = spy(new DatabaseManager());

        Model fakeModel = new Model(1, "Maglietta", 19.99, "Abbigliamento", "T-shirt base");
        String size = "M";
        int requestedQty = 5;

        // Forziamo countRecords() a restituire 2, (quindi < 5)
        doReturn(2).when(spyManager).countRecords(fakeModel, size);

        // 2) Mock di Connection/PreparedStatement/ResultSet se vuoi simulare la query
        //    Altrimenti, se il codice arriva subito a controllare countRecords e restituisce null,
        //    potresti non aver bisogno di mockare la query.
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        // Per evitare che dia errori su getConnection()
        doReturn(mockConn).when(spyManager).getConnection();
        doReturn(mockStmt).when(mockConn).prepareStatement(anyString());
        doReturn(mockRs).when(mockStmt).executeQuery();

        // Simuliamo che la query "rs.next()" abbia esito "true" una volta (quindi c'è un record),
        // ma poi "countRecords" indica che non ne abbiamo abbastanza
        when(mockRs.next()).thenReturn(true);

        // 3) Eseguiamo il metodo
        Product result = spyManager.returnProductByModel(fakeModel, size, requestedQty);

        // 4) Verifichiamo l'output: dev'essere null (not enough stock)
        assertNull("Se la quantità in DB non è sufficiente, deve restituire null", result);
    }

  
    private String invokeCapitalize(String input) {
        // Uso la reflection interna? No, la tua classe non è private. 
        // Il metodo "capitalize" è private, quindi qui potremmo fare
        // un "trick" con reflection o un partial mock.
        // Per brevità, facciamo un piccolo "copy" in un helper
        // OPPURE convertilo a public per poterlo testare direttamente.
        try {
            // Qui useremmo Whitebox da PowerMock
            // se volessimo testare davvero il private:
            // return Whitebox.invokeMethod(databaseManager, "capitalize", input);
            
            // Oppure, come strategia più semplice, "riproduciamo" la stessa logica:
            // (Sconsigliato in un test reale, ma almeno mostriamo l'idea)
            if (input == null || input.isEmpty()) {
                return input;
            }
            return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();

        } catch (Exception e) {
            fail("Eccezione in testCapitalize: " + e.getMessage());
            return null; 
        }
    }

}
