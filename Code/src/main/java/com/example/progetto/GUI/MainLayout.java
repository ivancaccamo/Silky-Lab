package com.example.progetto.GUI;

import com.example.progetto.backend.Current;
import com.example.progetto.backend.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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


/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout{
	
    private H1 viewTitle;
    private Footer footer;
    private Footer logout;
    private User user;
    private static MainLayout instance;
    private SideNav nav;
    private Scroller scroller;
    @Autowired
    public MainLayout() {
    	instance = this;
    	footer = new Footer();
    	logout = new Footer();
    	nav = new SideNav();
    	createFooter();
    	
    	setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent(); 
        scroller = new Scroller(nav);
        addToDrawer(scroller);
        addToDrawer(footer);
        if (user != null) createLogout();
    }

    public static MainLayout getInstance() {
		return instance;
	}

	public static void setInstance(MainLayout instance) {
		MainLayout.instance = instance;
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
        
        createNavigation();

        addToDrawer(header);
    }

    public void createNavigation() {
    	user = Current.getUser();
    	List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();

        menuEntries.forEach(entry -> {
        	if (user != null && user.getRole().equals("ADMIN") && (entry.title().equals("Carrello") || entry.title().equals("Profilo Personale"))) {
                return; // Salta questa voce
            }
        	
        	if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        }); 
    }
    
    /*public void updateNavigation() {
    	createNavigation();
    }*/

    private void createFooter() {  
        // Icona utente
        Icon userIcon = new Icon("vaadin", "user");
        userIcon.setSize("26px");
        userIcon.addClassNames(LumoUtility.Margin.Right.MEDIUM, LumoUtility.Margin.Left.SMALL);

        // Nome dell'utente corrente
        Span userNameSpan;
        String route=null;
        user = Current.getUser();
        if(user != null) {
 
        	String currentUserName = "Ciao "+user.getName(); // Metodo per ottenere il nome dell'utente
        	userNameSpan = new Span(currentUserName);
        	userNameSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD,LumoUtility.Margin.Left.MEDIUM);
        	route = "Profile";

        }else {
        	
        	userNameSpan = new Span("Login");
        	userNameSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD,LumoUtility.Margin.Left.MEDIUM); 	
        	route = "Login"; 	 
        }
    	
        Anchor profileLink = new Anchor(route, userIcon, userNameSpan); 
        
        profileLink.addClassNames(LumoUtility.TextColor.SECONDARY);
        footer.add(profileLink); 
        footer.getStyle().set("background", "#dcdcdc");
        footer.getStyle().set("border-radius", "30px");
        footer.getStyle().set("padding", "10px");
        footer.getStyle().set("margin", "10px");
        footer.getStyle().set("cursor", "pointer");
    }
    
    private void createLogout() {
    	// Creazione dell'icona per il logout
        Icon logoutIcon = new Icon("vaadin", "sign-out");
        logoutIcon.setSize("24px");
        logoutIcon.addClassNames(LumoUtility.Margin.Right.SMALL, LumoUtility.Margin.Left.SMALL);

        // Creazione del testo "Logout"
        Span logoutText = new Span("Logout");
        logoutText.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD,LumoUtility.Margin.Left.SMALL);
        
        // Creazione del contenitore cliccabile senza bottone
        HorizontalLayout logoutContainer = new HorizontalLayout(logoutIcon, logoutText);
        logoutContainer.addClassNames(LumoUtility.TextColor.SECONDARY);
        logoutContainer.addClickListener(event -> {
            // Esegui il logout
            Current.ExitUser(); // Metodo per il logout (assicurati che esista)
            UI.getCurrent().getPage().executeJs("window.location.href = $0", "");
        });
        logout.add(logoutContainer);
        logout.getStyle().set("background", "#dcdcdc");
        logout.getStyle().set("border-radius", "30px");
        logout.getStyle().set("padding", "10px");
        logout.getStyle().set("margin", "10px");
        logout.getStyle().set("cursor", "pointer");
        addToDrawer(logout);
    }
}
