package com.example.progetto.GUI.admin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;

public class AdminViewTest {

    @BeforeEach
    public void setUp() {
        Current.setCurrentUser(null);
        Current.setCategoryView(null);
    }

    @Test
    public void testNoAdminAccess() {
        User client = new User();
        client.setRole("CLIENT");
        Current.setCurrentUser(client);
        AdminView view = new AdminView();
        boolean noAccessFound = view.getChildren()
            .anyMatch(comp -> comp.getClass().getSimpleName().equals("NoAdminAccess"));
        assertTrue(noAccessFound);
    }

    @Test
    public void testAdminAccess() {
        User admin = new User();
        admin.setRole("ADMIN");
        Current.setCurrentUser(admin);
        Current.setCategoryView("TestCategory");
        AdminView view = new AdminView();
        boolean h1Found = view.getChildren()
            .filter(comp -> comp instanceof H1)
            .map(comp -> ((H1) comp).getText())
            .anyMatch(text -> text.contains("TestCategory"));
        assertTrue(h1Found);
        boolean noAccessFound = view.getChildren()
            .anyMatch(comp -> comp.getClass().getSimpleName().equals("NoAdminAccess"));
        assertFalse(noAccessFound);
    }
}
