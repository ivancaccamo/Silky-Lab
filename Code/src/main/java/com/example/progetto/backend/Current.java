package com.example.progetto.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Current{
	private static Current theInstance;
    private static User currentUser;
    private final static List<MyListener> listeners = new ArrayList<>();
    private static String productView;
    private static String categoryView;
    
    public void addListener(MyListener listener) {
        listeners.add(listener);
    }
    
      
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


	public static List<MyListener> getListeners() {
		return listeners;
	}


	public static User getUser() {
        return currentUser;
    }
    public static Current getInstance() {
    	if(theInstance == null) {
    		theInstance = new Current();
    	}
    	return theInstance;
    }
    public static void setUser(User user) {
    	
    	if(theInstance == null) {
    		theInstance = new Current();
    	}
        currentUser = user;
        notifyListeners();
    }
    private static void notifyListeners() {
        for (MyListener listener : listeners) {
        	System.out.println("ciao");
            listener.onMethodCalled();
            
        }
    }

	private Current() {
    	
    }
    public static Current ExitUser() {
		return null;	
    }
}