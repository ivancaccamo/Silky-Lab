package com.example.progetto.backend;

import org.springframework.stereotype.Component;

@Component
public class CurrentUser{
	private static CurrentUser theInstance;
    private static User currentUser;
    

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
    }
    private CurrentUser() {
    	
    }
    public static CurrentUser ExitUser() {
		return null;	
    }
}