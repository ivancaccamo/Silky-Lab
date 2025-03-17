package com.example.progetto.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
/**
 * Classe che gestisce il contesto corrente dell'applicazione, mantenendo informazioni
 * come l'utente loggato e alcune viste selezionate.
 * <p>
 * Utilizza un approccio di tipo "singleton" tramite variabili e metodi statici
 * per fornire un unico punto di accesso ai dati condivisi.
 * </p>
 */
@Component
public class Current{
	private static Current theInstance;
    private static User currentUser;
    private static String productView;
    private static String categoryView;

      
    public static Current getTheInstance() {
		return theInstance;
	}


	public static void setTheInstance(Current theInstance) {
		Current.theInstance = theInstance;
	}


	public static User getCurrentUser() {
		return currentUser;
	}


	public static void setCurrentUser(User currentUser) {
		
		Current.currentUser = currentUser;
	}


	public static String getProductView() {
		return productView;
	}


	public static void setProductView(String productView) {
		Current.productView = productView;
	}


	public static String getCategoryView() {
		return categoryView;
	}


	public static void setCategoryView(String categoryView) {
		Current.categoryView = categoryView;
	}


	public static User getUser() {
        return currentUser;
    }
    public static void setUser(User user) {
    	
    	if(theInstance == null) {
    		theInstance = new Current();
    	}
        currentUser = user;
    }
	private Current() {
    	
    }
	/**
     * Esegue il logout dell'utente, rimuovendo l'istanza dal carrello
     * e annullando il riferimento all'utente corrente.
     */
    public static void ExitUser() {
    	Cart.removeAll();
    	currentUser=null;
    }
}