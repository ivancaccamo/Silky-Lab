package com.example.progetto.GUI;

import com.example.progetto.backend.CurrentUser;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private H1 viewTitle;
    private User user = CurrentUser.getUser();
    @Autowired
    public MainLayout(ApplicationEventPublisher eventPublisher) {
    	setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        
    }
    
    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("Silky Lab");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> {
            if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        });

        return nav;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames(LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.BETWEEN, LumoUtility.Padding.MEDIUM);
        // Icona utente
        Icon userIcon = new Icon("lumo", "user");
        userIcon.setSize("50px");
        userIcon.addClassNames(LumoUtility.Margin.Right.SMALL);

        // Nome dell'utente corrente
        Span userNameSpan;
        String route=null;
        
        if(user != null) {
 
        	String currentUserName = user.getName(); // Metodo per ottenere il nome dell'utente
        	userNameSpan = new Span(currentUserName);
        	userNameSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD,LumoUtility.Margin.Left.SMALL);
        	route = "Profile";
        	
        }else {
        	
        	userNameSpan = new Span("Login");
        	userNameSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD,LumoUtility.Margin.Left.SMALL); 	
        	route = "login"; 
        	
        }
        
       

        // Link alla pagina del profilo
        Anchor profileLink = new Anchor(route, userIcon, userNameSpan); // Cambia "profile" con il tuo route
        profileLink.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER);
        profileLink.getStyle().set("text-decoration", "none");
        footer.add(profileLink);
        return footer;
    }
 
    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
