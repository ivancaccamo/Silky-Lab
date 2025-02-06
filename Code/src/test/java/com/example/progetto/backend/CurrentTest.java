package com.example.progetto.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CurrentTest {

   
    @BeforeEach
    public void setUp() throws Exception {
        // Resettiamo l'istanza singleton e gli altri attributi statici
        resetStaticField("theInstance", null);
        resetStaticField("currentUser", null);
        resetStaticField("productView", null);
        resetStaticField("categoryView", null);
        // Svuotiamo la lista dei listener
    }
    
    
     // Metodo di supporto per resettare i campi statici tramite reflection.
    
    private void resetStaticField(String fieldName, Object value) throws Exception {
        Field field = Current.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }
  
    @Test
    public void testSetAndGetUser() {
        // Creiamo un utente di test
        User testUser = new User();
        // Utilizziamo il metodo setUser
        Current.setUser(testUser);
        // Verifichiamo che l'utente impostato sia quello restituito dai metodi getUser e getCurrentUser
        assertEquals(testUser, Current.getUser());
        assertEquals(testUser, Current.getCurrentUser());
        
        // Verifichiamo che l'istanza singleton sia stata creata
        assertNotNull(Current.getTheInstance());
    }
    
    
//      Testa il corretto funzionamento dei metodi set/get per il campo productView.
    
    @Test
    public void testSetAndGetProductView() {
        String productView = "TestProductView";
        Current.setProductView(productView);
        assertEquals(productView, Current.getProductView());
    }
    
   
//     Testa il corretto funzionamento dei metodi set/get per il campo categoryView.
    
    @Test
    public void testSetAndGetCategoryView() {
        String categoryView = "TestCategoryView";
        Current.setCategoryView(categoryView);
        assertEquals(categoryView, Current.getCategoryView());
    }
    
    @Test
    public void testExitUser() {
        User testUser = new User();
        Current.setUser(testUser);
        
        // Verifichiamo che l'utente sia stato impostato
        assertNotNull(Current.getUser());
        
        // Chiamando ExitUser l'utente corrente deve essere impostato a null
        Current.ExitUser();
        assertNull(Current.getUser());
    }

}

