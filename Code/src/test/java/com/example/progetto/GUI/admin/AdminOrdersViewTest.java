package com.example.progetto.GUI.admin;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@SpringBootTest
public class AdminOrdersViewTest {

    @BeforeEach
    public void setUp() {
        // Resetta l'utente corrente prima di ogni test
        Current.setCurrentUser(null);
    }
    @Test
    public void testNonAdminAccess() {
        User clientUser = new User();
        clientUser.setRole("CLIENT");
        clientUser.setEmail("client@example.com");
        Current.setCurrentUser(clientUser);

        AdminOrdersView view = new AdminOrdersView();
        VerticalLayout content = view.getContent();

        boolean foundNoAccess = content.getChildren()
            .anyMatch(comp -> comp.getClass().getSimpleName().equals("NoAdminAccess"));
        assertTrue(foundNoAccess);
    }
}
