package com.example.progetto.GUI.areaprivata;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;

public class LoginViewTest {

    @BeforeEach
    public void setUp() {
        Current.setUser(null);
    }

    @Test
    public void testLoginViewWhenUserIsNull() {
        LoginView view = new LoginView();
        Optional<Component> header = view.getContent().getChildren()
                .filter(comp -> comp instanceof H1 && ((H1) comp).getText()
                        .equals("Per accedere a tutte le funzionalità del sito esegui l'accesso"))
                .findFirst();
        Optional<Component> loginTitle = view.getContent().getChildren()
                .filter(comp -> comp instanceof H3 && ((H3) comp).getText().equals("Log in"))
                .findFirst();
        assertTrue(header.isPresent());
        assertTrue(loginTitle.isPresent());
    }

    @Test
    public void testLoginViewWhenUserIsNotNull() {
        User user = new User();
        user.setName("TestUser");
        Current.setUser(user);
        LoginView view = new LoginView();
        Optional<Component> greeting = view.getContent().getChildren()
                .filter(comp -> comp instanceof H2 && ((H2) comp).getText().contains("Ciao TestUser"))
                .findFirst();
        Optional<Component> closeButton = view.getContent().getChildren()
                .filter(comp -> comp instanceof Button && ((Button) comp).getText().equals("Chiudi"))
                .findFirst();
        assertTrue(greeting.isPresent());
        assertTrue(closeButton.isPresent());
    }
}
