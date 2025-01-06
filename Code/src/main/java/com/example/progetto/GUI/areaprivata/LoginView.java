package com.example.progetto.GUI.areaprivata;

import com.example.application.services.UserService;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan
@Route("login")
public class LoginView extends VerticalLayout {

    @Autowired
    private UserService userService;

    public LoginView() {
        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login", event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();
            User user = userService.authenticate(email, password);
            if (user != null) {
                if ("ADMIN".equals(user.getRole())) {
                    getUI().ifPresent(ui -> ui.navigate("admin-home"));
                } else if ("CLIENT".equals(user.getRole())) {
                    getUI().ifPresent(ui -> ui.navigate("client-home"));
                }
            } else {
                Notification.show("Email o password non validi.");
            }
        });

        add(emailField, passwordField, loginButton);
    }
}
