package com.example.progetto.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser{
	private static CurrentUser theInstance;
    private static User currentUser;
    private final static List<MyListener> listeners = new ArrayList<>();
    
    public void addListener(MyListener listener) {
        listeners.add(listener);
    }
      
    public static User getUser() {
        return currentUser;
    }
    public static CurrentUser getInstance() {
    	if(theInstance == null) {
    		theInstance = new CurrentUser();
    	}
    	return theInstance;
    }
    public static void setUser(User user) {
    	
    	if(theInstance == null) {
    		theInstance = new CurrentUser();
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

	private CurrentUser() {
    	
    }
    public static CurrentUser ExitUser() {
		return null;	
    }
}