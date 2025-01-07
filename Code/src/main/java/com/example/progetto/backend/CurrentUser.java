package com.example.progetto.backend;

import org.springframework.stereotype.Component;

@Component
public class CurrentUser{
    private static User currentUser;
    

    public static User getUser() {
        return currentUser;
    }

    public static void setUser(User user) {
        currentUser = user;
    }
    private CurrentUser() {
    	
    }
    public static CurrentUser ExitUser() {
		return null;	
    }
}